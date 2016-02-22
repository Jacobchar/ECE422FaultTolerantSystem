#include <stdio.h>
#include <jni.h>
#include "dataSorter.h"

/* C sourve for the native insertion search algorithm */
/* Jacob Charlebois, February 2016 */

int[] insertionSort(jint *, int);
/* Insertion Sort algorithm */


/*
* Fancy JNI stuff here
*
*
*
**/


/*--------------------------------------------------------*/ 
int[] insertionSort(jint *list, int length){
/* Insertion Sort algorithm */
/* Parent: JNI entry point */
	for(size_t i = 1; i < length; i++) {
		int temp = list[i];
		size_t j = i;
		while(j > 0 && tmp < list[j - 1])  {
			list[j] = list[j - 1];
			j --;
		}
		list[j] = temp;
	}
}