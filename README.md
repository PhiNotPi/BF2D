# BF2D
Brute-forcing 2D BF programs

The main method is located in Grid.java and is currently set to output the validation run for the original PPCG challenge.

This is a simple brute-forcer:

1. Generate a list of valid BF programs. (note: in 2D BF there aren't really any optimizations we can do here)
2. In roughly shortest-first order, iterate through all unique pairs of programs.
3. See if they pass all testcases.
