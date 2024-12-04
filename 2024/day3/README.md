# Day 3 Review

### Input
A string of "corrupted" instructions including the following:
- mul(x,y) where x, y are one to three digit integers
- do()
- don't()

Example:

`$^mul(34,84)cdjdo()idon't()mul(98,293)le37(do()%mul(263,47)fga`

### Part 1 Task
Find each occurence of the mul(x,y) instruction, multiply x * y, and find the sum of the products.

### Part 2 Task
Only find the sum of the products where do() is the most recent instruction. The mul(x,y) function should not be included where don't() was the last instruction. Start with multiplication enabled, as if the input string started with "do()."
