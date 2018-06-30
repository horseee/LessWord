import numpy as np
import numpy as np
import time
import scipy.sparse
from scipy.sparse import lil_matrix
import math
import argparse


parser = argparse.ArgumentParser()
parser.add_argument('--user_record_count',type=int,default=0,help='the number of record')
parser.add_argument('--word_number', type=int, default=0, help='the return word number')
args = parser.parse_args()

# load train data
user_count = args.user_record_count
word_count = 6866
user_to_word =lil_matrix((word_count, user_count))
#user_to_word = np.ones((user_count, word_count))

f = open("data/reciting_record.dat", 'r')
ptr = 0
for line in f:
    recordInfo = line.strip("\n")
    wordlist = recordInfo.split(' ')
    for word in wordlist:
        wordset, index = word.split(',')
        wordset, index = int(wordset), int(index)
        if wordset == 0:
            user_to_word[index, ptr] = 1
        elif wordset == 1:
            user_to_word[3596 + index, ptr] = 1
        else:
            user_to_word[4596 + index, ptr] = 1
    ptr = ptr + 1
print("Finish Getting All reciting records")
f.close()

f = open("data/not_reciting_record.dat", 'r')
ptr = 0
for line in f:
    recordInfo = line.strip("\n")
    wordlist = recordInfo.split(' ')
    for word in wordlist:
        wordset, index = word.split(',')
        wordset, index = int(wordset), int(index)
        if wordset == 0:
            user_to_word[index, ptr] = -1
        elif wordset == 1:
            user_to_word[3596 + index, ptr] = -1
        else:
            user_to_word[4596 + index, ptr] = -1
    ptr = ptr + 1
print("Finish Getting All not_reciting records")
f.close()

def CalSim(setA, setB):

    setA_col = setA.nonzero()[1]
    setB_col = setB.nonzero()[1]
    score = 0
    for col in setA_col:
        if setB[0, col] != 0:
            score += setB[0, col] * setA[0, col]
    if score == 0:
        return 0
    score = score / (np.sqrt(setA_col.shape[0])* np.sqrt(setA_col.shape[0]))
    
    #if dot == 0:
    #    return 0
    #lenA = float(math.pow(len(setA), 0.5))
    #lenB = float(math.pow(len(setB), 0.5))
    #return dot*1.0 /(lenA * lenB)
    return score

user = 0
# total_score = 0 
# pre_time = time.clock()
# 
f = open("data/user_recite.dat", 'r')
train_index = []
not_train_index = []
ptr = 0
for line in f:
    recordInfo = line.strip("\n")
    wordlist = recordInfo.split(' ')
    if ptr == 0:
        for word in wordlist:
            wordset, index = word.split(',')
            wordset, index = int(wordset), int(index)
            if wordset == 0:
                train_index.append(index)
            elif wordset == 1:
                train_index.append(3596 + index)
            else:
                train_index.append(4596 + index)
    else:
        for word in wordlist:
            wordset, index = word.split(',')
            wordset, index = int(wordset), int(index)
            if wordset == 0:
                not_train_index.append(index)
            elif wordset == 1:
                not_train_index.append(3596 + index)
            else:
                not_train_index.append(4596 + index)
    ptr = ptr + 1
print("Finish Getting All user_recite_word")
f.close()

WordScore = np.zeros(word_count, dtype = "float32")
for recite_word in train_index:
    setA = user_to_word[recite_word]
    for w in range(word_count):
        if w in train_index:
            continue
        setB = user_to_word[w, :]
        SimilarityScore = CalSim(setA, setB)
        WordScore[w] += SimilarityScore

for recite_word in not_train_index:
    setA = user_to_word[recite_word]
    for w in range(word_count):
        if w in train_index:
            continue
        setB = user_to_word[w, :]
        SimilarityScore = CalSim(setA, setB)
        WordScore[w] -= SimilarityScore
    
indices = np.argsort(WordScore)

f = open("data/user_word_result.dat", 'w')
for i in range(args.word_number):
    f.write(str(indices[i]) + " ")
f.close()

    
    #correct = 0
    #score = 0
    #  test map100
    #for i in range(100):
    #    if indices[word_count -1 -i] in result_set[user]:
    #        print("%d %d %5f" % (i+1, indices[word_count -1 -i], SongScore[indices[word_count -1 -i]]))
    #        correct += 1
    #        score += correct / (i+1) / len(result_set[user])
    #print("%d/%d" % (correct,len(result_set[user])))
    #print("%5f" % score)
    #total_score += score
