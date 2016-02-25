#include <stdio.h>
#include <jni.h>
#include "dataSorter.h"

/* C sourve for the native insertion search algorithm */
/* Jacob Charlebois, February 2016 */
JNIEXPORT jintArray JNICALL Java_InsertionSort_insertionSort
  (JNIEnv *env, jobject obj, jintArray array, jdouble probability);{

    jsize = length;
  	jint *myCopy;
    jintArray sortedArray;
  	jboolean *is_copy =0;

    length = (*env)->GetArrayLength(env, buf);
  	myCopy = (jint *) (*env)->GetIntArrayElements(env, array, is_copy);
  	if (myCopy == NULL){
  		printf("Cannot obtain array from JVM\n");
  		exit(0);
  	}
  	
  	jintArray sortedList = (*env)->NewIntArray(env, length);
  	memAccesses = 0;
  	
  	int i, j;
	for(i = 1; i < length; i++) {
		int temp = sortedList[i];
		j = i;
		while(j > 0 && temp < myCopy[j - 1]) {
			myCopy[j] = myCopy[j - 1];
			j --;
			memAccesses += 5;
		}
		myCopy[j] = temp;
		memAccesses ++;
	}

	double hazard = probability * memAccesses;
	double r = ((double) rand() / RAND_MAX);
	
	if(r > 0.5 && r < (0.5 + hazard)) {
		(*env)->SetIntArray(env, sortedList, 0, 0, myCopy);
	} else {
		(*env)->SetIntArray(env, sortedList, 0, length, myCopy);
	}
  	return sortedList;
  }