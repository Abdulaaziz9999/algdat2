# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Navn Navnesen, S123456, s123456@oslomet.no
* ...

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Per har hatt hovedansvar for oppgave 1, 3, og 5. 
* Else har hatt hovedansvar for oppgave 2, 4, og 6. 
* Fatima har hatt hovedansvar for oppgave 7 og 8. 
* Vi har i fellesskap løst oppgave 10. 

# Oppgavebeskrivelse
Task 1
I Made a doubly linked list based on table a. checked if table is null and threw an exception if it is. then entered the table and retrieved the first non null value
and made it head. used a temporary variable to set the pointer to head.next. Looped through the table and put all non null values in the help variable.next; finally set the tail.
Also created the methods number which returned the number of nodes, and boolean tom which checked if the head is null and returned true if it was.

 Task 2)

Used Stringbuilder to append all the values in the list. checked if the list is empty and appended an empty parenthesis if it was. If not, I appended the value to the head, appended a comma and started a loop.
The loop started from head.next and appended all the non-zero values and set the pointer to next, repeating the process until head.next was zero.
For the reverse string, I used the same concept as exercise 1.a but started from the tail and set the pointer to tail.prev in each iteration of the loop.

Task 3)

Find node gets an index as an argument and checks whether the index is closer to head or tail and starts based on that. if index is closer to head then we start from head and set the pointer to head.next in each iteration
of the loop, we stop when int i > index, then the auxiliary variable.next is in the correct place and then we retrieve the node that is in this place. If the index was closer to the tail, we repeated the same concept, only that the auxiliary variable started at the tail
and set the pointer to tail.previous all the way to int i > index, then the auxiliary variable.previous became the index place we wanted it in. and then we returned the node that is in this index.
the get method only called finnode and returned node.value.
In the update method we used objects.requiresnonnull to check if the value we were going to insert was null. We retrieved the old value using the find node and stored it in an auxiliary variable, we replaced the old value with the new value that came in as an argument
and returned old value.
For the sublist method, I created a helper method to control the arguments. made a new doubly linked list using constructor and called the method on from position on finnnode and set it as head. I used the insert method to insert the values ​​based on the length of the sublist.
took length -- after each entry and set the pointer to auxiliary variable.next in each while iteration.

task 4
first checked if the value is null, returned -1 if it was. then used a helper variable and set it to head, used a for loop to loop through the list and compared the value of the helper variable with the variable that comes as argument, when the value is equal then we return i which is the index of the value.
if the value is not found I return -1. Boolean indextil only calls the indextil method and returns true if indextil does not return -1.


task 5
Task 5)

First checks if the value coming in as an argument is null, throws an exception if it is. Then checks the index and checks if it is less than zero or greater than number. Then checks if the list is empty and the index is zero, if they are then we put the node as the only one in the list, and set both head and tail pointers to dne.
then I check if index is the last in the list, then I change the tail pointers and set the new node to tail. do the same if index is zero and the list is not empty, then the new node becomes the head and swaps the head pointers. If none of these cases happens, then the new node must be placed in the middle, then we loop through the list and set the pointer to the auxiliary variable .next
in each iteration. stops the loop when we reach the index and then auxiliary variable.next is the index we are going to put the node in, then we temporarily set.next to new Node and change the pointers to the previous and next node. Increases number and changes by 1.


Task 6)
checks whether the index we are going to remove is valid by calling index check. checks if the index is first in the list, if it is we swap the head pointers and set head to head.next. Then checks whether there are elements after head, if not then head and tail are set to zero. Then checks if the index is the last element in the list, if it is then we swap the tail and tail pointers.
If not, then it is in the middle, then we loop through the list until we reach the index we want to remove and exchange the pointers to the previous and next node. when we find the index to be removed in each case, we store the value in an auxiliary variable, and return it after increasing changes and count by 1.
The method boolean remove uses the same thinking as the method previously, only that what we check for is not the index but whether the value of the node is the same as the value that comes in as an argument. if it is then we swap pointers based on where it is in the list, finally we decrement the count by 1 and return the value that is removed from the list.