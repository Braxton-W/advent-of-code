# Day 9 Review

### Input
A single inline string of digits.

Example:
```
2333133121414131402
```

### Background
Each digit represents blocks of either a file or free space in memory. Every second digit is a free space block, the first being a file block.

### Part 1 Task
Create a representation of the memory using the file index position to represent file space.

Left align the memory by moving the right-most space to the left-most free space.

Take the sum of the products of the index position in the most recent result and value (the file index position).

Example: 
```
2333133121414131402
v
00...111...2...333.44.5555.6666.777.888899
v
0099811188827773336446555566..............
v
0 * 0 + 1 * 0 + 2 * 9 + 3 * 9 + 4 * 8 + 5 * 1 + . . . + 25 * 5 + 26 * 6 + 27 * 6
```

### Part 2 Task
Use the representation of memory from part 1.

Again, left align the memory but this time by moving whole blocks when possible from the right to the left-most free space.

Take the sum of the products of the index position in the most recent result and value (the file index position).

Example: 
```
2333133121414131402
v
00...111...2...333.44.5555.6666.777.888899
v
00992111777.44.333....5555.6666.....8888..
v
0 * 0 + 1 * 0 + 2 * 9 + 3 * 9 + 4 * 2 + 5 * 1 + . . . + 38 * 8 + 39 * 8
```
