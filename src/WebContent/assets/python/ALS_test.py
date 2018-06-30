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
parser.add_argument('--word_number', type=int, default=0, help='the return word number')
parser.add_argument('--user_id', type=int, default=0, help='the user ID')
parser.add_argument('--user_record_count',type=int,default=0,help='the number of record')
args = parser.parse_args()

k = 500
user_count = args.user_record_count
word_count = 6866
print("Begin Writing result to file ...")
Input_X = np.fromfile("data/Input_X.bin",dtype = np.float32)
Input_Y = np.fromfile("data/Input_Y.bin",dtype = np.float32)
Input_X = np.mat(Input_X.reshape(k, user_count))
Input_Y = np.mat(Input_Y.reshape(k, word_count))

f= open('data/user_word_result.dat', 'w')
user_ID = args.user_id
    
# rating for ALS
Y = tf.placeholder(dtype=tf.float32,shape=Input_Y.shape,name="Y")
P = tf.placeholder(dtype=tf.float32, shape=(k, 1), name="P")
product = tf.matmul(tf.transpose(P), Y)
# sess = tf.Session(config=tf.ConfigProto(gpu_options = gpu_options))
sess = tf.Session()
resultMatrix = sess.run(product, {P:Input_X[:, user_ID], Y:Input_Y})
    
indiceALS = np.argsort(resultMatrix[0, :])
f = open("data/user_word_result.dat", 'w')
for i in range(args.word_number):
    f.write(str(indiceALS[i]) + " ")
        
f.close()





