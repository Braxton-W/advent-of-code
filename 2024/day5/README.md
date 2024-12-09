# Day 5 Review

### Input
In a single file, a list of rules in the form `x|y` followed by a list of updates in the form `x,y,z`.

Example:

```
47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
```

### Background
For an update to be ordered correctly, for each rule where x and y are in the update, x must come before y.

### Part 1 Task
Take the sum of the middle value in each correctly ordered update.

### Part 2 Task
Take the sum of the middle value in each incorrectly ordered update, after correctly ordering the updates according to the rules.
