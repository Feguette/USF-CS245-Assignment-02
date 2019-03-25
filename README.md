# CS 245 (Spring, 2019) Assignment 2
@author Jason Liang


## HybridSort
* Hybridization between quicksort with randomized index and quadratic sort
* Insertion sort was chosen as the quadratic sort due to its faster runtime compared to other O(n^2) sorting algorithms
which would when the size of the array is less than or equal to {@code MAX_LIMIT}
* [See google sheets for more data on Insertion sort](https://docs.google.com/spreadsheets/u/1/d/15ewFukHKzX-k4qXkG9Co_vuDtdN1jh1fTLcg1JW4whs/edit?usp=drive_web&ouid=108260742411014476318)
* Insertion sort, compared to other simple and common sorting O(n^2) algorithms, is faster, but paired with quicksort
there is unfortunetely no distinguishable runtime advantage compared to using pure quicksort with MAX_LIMIT at 8 or 10,
* HybridSortTest.java and/or HybridSort.java may be flawed, but perhaps only certain numbers work as a limit to start using
quadratic sort.
* Quicksort works when the size of the subarray is domain (MAX_LIMIT, inf),
while quadratic sort is used when size is domain [0, MAX_LIMIT].
* Run HybridSortTest.java to check HybridSort.java implementation.
* [3 Graphs of MAX_LIMIT at 8, 10, and 20](https://docs.google.com/spreadsheets/d/1z47XZFkWiQFQCuW-4UbO4_WVLb9B7CxVBEtJ6s9fEcM/edit?usp=sharing)


## External Sort
* First splits up input file to Math.ceil(n/k) files, placing it into {@Path ./Results/TempFiles/0}
* Then, all files in {@Path ./Results/TempFiles/0} are sorted with Sorting.quickSort() and added back into its respective files
* Recursively merges all files by merging two files and placing it into {@Path ./Results/TempFiles/1},
then {@Path ./Results/TempFiles/2}, and so on, until all files are merged.
* Run ExternalSortTest.java to see ExternalSort.java implementation. You may have to change other values