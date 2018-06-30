from __future__ import print_function
import os
import logging

import numpy as np
import scipy.sparse
from scipy.sparse import lil_matrix
import tensorflow as tf
import argparse
import time

from numpy.random import RandomState

parser = argparse.ArgumentParser()
parser.add_argument('--user_record_count',type=int,default=0,help='the number of record')
args = parser.parse_args()

"""
Read User-Word Train triplets
"""
# load train data
user_count = args.user_record_count
word_count = 6866
user_to_word =lil_matrix((word_count, user_count))

indices = []
data = []
ratings = []

f = open("data/reciting_record.dat", 'r')
ptr = 0
for line in f:
    recordInfo = line.strip("\n")
    wordlist = recordInfo.split(' ')
    for word in wordlist:
        wordset, index = word.split(',')
        wordset, index = int(wordset), int(index)
        data.append(1)
        if wordset == 0:
            user_to_word[index, ptr] = 1
            ratings.append([ptr, index, 1])
            indices.append([ptr, index])
        elif wordset == 1:
            user_to_word[3596 + index, ptr] = 1
            ratings.append([ptr, 3596 + index, 1])
            indices.append([ptr, index + 3596])
        else:
            user_to_word[4596 + index, ptr] = 1
            ratings.append([ptr, 4596 + index, 1])
            indices.append([ptr, index + 4596])
    ptr = ptr + 1
print("Finish Getting All reciting records")
f.close()

f = open("data/not_reciting_record.dat", 'r')
ptr = 0
for line in f:
    recordInfo = line.strip("\n")
    wordlist = recordInfo.split(' ')
    for word in wordlist:
        data.append(-1)
        wordset, index = word.split(',')
        wordset, index = int(wordset), int(index)
        if wordset == 0:
            user_to_word[index, ptr] = -1
            ratings.append([ptr, index, -1])
            indices.append([ptr, index])
        elif wordset == 1:
            user_to_word[3596 + index, ptr] = -1
            ratings.append([ptr, 3596 + index, -1])
            indices.append([ptr, index + 3596])
        else:
            user_to_word[4596 + index, ptr] = -1
            ratings.append([ptr, 4596 + index, -1])
            indices.append([ptr, index + 4596])
    ptr = ptr + 1
print("Finish Getting All not_reciting records")
f.close()

#store data for ALS
#times = 1
#data.append(times)
#user_to_word[int(user), word_to_column_index[int(word)]] = times

train = np.array(ratings)
ArrayIndices = np.array(indices, dtype = np.int64)
ArrayData = np.array(data, dtype =np.float32)

"""
ALS for Latent Factor
"""
# initialize x, y, k and lambda
k = 500
reg_lambda = 12
Iteration_time = 300
Iter = 0

Input_X = np.mat(np.ones((k, user_count), dtype=np.float32))
Input_Y = np.mat(np.ones((k, word_count), dtype=np.float32))

X = tf.placeholder(dtype=tf.float32,shape=Input_X.shape,name="X")
Y = tf.placeholder(dtype=tf.float32,shape=Input_Y.shape,name="Y")
# User_To_Song_Sparse = tf.sparse_placeholder(dtype=tf.float32, shape=user_to_song.shape,name="User_To_Song_Sparse")
MatrixIndice = tf.placeholder(dtype=tf.int64, shape=ArrayIndices.shape, name="MatrixIndice")
MatrixData = tf.placeholder(dtype=tf.float32, shape=ArrayData.shape, name="MatrixData")
TF_Word_Count = tf.constant(dtype=tf.int64, value = word_count, name = "TF_Word_Count")
TF_User_Count = tf.constant(dtype=tf.int64, value = user_count, name = "TF_User_Count")

User_To_Song_Sparse = tf.SparseTensor(indices = MatrixIndice, values = MatrixData, dense_shape=[TF_User_Count, TF_Word_Count])

y = tf.transpose(tf.sparse_tensor_dense_matmul(tf.sparse_transpose(User_To_Song_Sparse), tf.transpose(
                                                    tf.matmul(
                                                        tf.matrix_inverse(
                                                            tf.matmul(X,tf.transpose(X,perm=(1,0))) + np.mat(np.eye(k) * reg_lambda)),X))))
x = tf.transpose(tf.sparse_tensor_dense_matmul(User_To_Song_Sparse, tf.transpose(
                    tf.matmul(
                        tf.matrix_inverse(
                            tf.matmul(y,tf.transpose(y,perm=(1,0))) + np.mat(np.eye(k) * reg_lambda)),y))))
#x = tf.matmul(tf.matrix_inverse(tf.matmul(y,tf.transpose(y,perm=(1,0))) + np.mat(np.eye(k) * reg_lambda)),y)
#x = tf.matmul(tf.matrix_inverse(tf.matmul(Y,tf.transpose(Y,perm=(1,0))) + np.mat(np.eye(k) * reg_lambda)),Y), tf.transpose(User_To_Song_Sparse,perm=(1,0)), b_is_sparse=True)

#gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction = 0.3) # change the fraction if needed
#with tf.Session(config=tf.ConfigProto(gpu_options = gpu_options)) as sess:

with tf.Session() as sess:
    init_op = tf.global_variables_initializer()
    sess.run(init_op)

    print("Training ALS model ...")
    print("Run...")
    c = time.time()
    for i in range(Iteration_time):
        Input_Y, Input_X = sess.run([y, x], {X: Input_X, Y:Input_Y, MatrixIndice:ArrayIndices, MatrixData:ArrayData})
        print("Iteration: %d/%d" % (i+1, Iteration_time))

        if i/ 20 == 0:
            Input_X.tofile("data/Input_X.bin")
            Input_Y.tofile("data/Input_Y.bin")
    print("Time cost: %f" % (time.time()-c))
    