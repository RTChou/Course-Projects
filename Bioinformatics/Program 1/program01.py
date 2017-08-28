#!/usr/bin/env python3

# encoding: utf8
# author: Renee Ti Chou

"""
This program prompts the user for an oligonucleotide sequence,
and prints the reverse complement of that sequence to STDOUT.
The iteration of prompting continues until the user enters a
sequence that is not consists of A, C, G, or T.
"""

import re

def reverse_complement(sequence):
    """
    This method returns the reverse complement of
    the sequence that is passed into the method
    """
    # reverse the sequence
    seq = sequence[::-1].upper()
    # complement the sequence
    seq = seq.replace("A", "t")
    seq = seq.replace("C", "g")
    seq = seq.replace("G", "c")
    seq = seq.replace("T", "a")
    return seq.upper()

def main():
    # prompt the user for the sequence
    sequence = input("Enter an oligonucleotide sequence: ")
    while not re.search(r"[^ACGT]", sequence):
        rev_com_seq = reverse_complement(sequence)
        print("User input:         {0}".format(sequence))
        print("Reverse complement: {0}".format(rev_com_seq))
        # continue prompting
        sequence = input("Enter an oligonucleotide sequence: ")

    # print an error information that the input is invalid
    # and terminate the prompting
    print("User input:         {0}".format(sequence))
    print("Invalid character(s): should be A, C, G, or T")

if __name__ == '__main__':
    main()
