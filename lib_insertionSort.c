#include <stdio.h>
#include <jni.h>
#include "dataSorter.h"

/* C sourve for the native insertion search algorithm */
/* Jacob Charlebois, February 2016 */

int[] insertionSort(jint *, int);
/* Insertion Sort algorithm */

JNIEXPORT jintArray JNICALL Java_InsertionSort_insertionSort
  (JNIEnv *env, jobject obj, jintArray buf, jdouble prob);{

    jsize = len;
  	jint *myCopy;
    jintArray sortedArray;
  	jboolean *is_copy =0;

    len = (*env)->GetArrayLength(env, buf);
  	myCopy = (jint *) (*env)->GetIntArrayElements(env, array, is_copy);
  	if (myCopy == NUL){
  		printf("Cannot obtain array from JVM\n");
  		exit(0);
  	}

  	intArray = insertionSort(myCopy, length);
  	return intArray;
  }

/*--------------------------------------------------------*/ 
int[] insertionSort(jint *list, int length){
/* Insertion Sort algorithm */
/* Parent: JNI entry point */
	for(size_t i = 1; i < length; i++) {
		int temp = list[i];
		size_t j = i;
		while(j > 0 && temp < list[j - 1])  {
			list[j] = list[j - 1];
			j --;
		}
		list[j] = temp;
	}
}