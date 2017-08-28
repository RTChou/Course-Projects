#!/usr/bin/env python3

# encoding: utf8
# author: Renee Ti Chou

"""
The program takes the command line arguments and searches for the queried feature
in a gff file. It prints out the data that are used for query the feature along with
the sequence of the feature in a FASTA format to STDOUT.
"""

import argparse
import re
import question_4

def print_sequence_width(seq, width=60):
    """
    This method prints the sequence in the format of a
    specific number of characters per line. The default
    number is 60.
    """
    print("\n".join(seq[i:i + width] for i in range(0, len(seq), width)))

def main():
    # parse the command line arguments into args
    parser = argparse.ArgumentParser(description="This is a script that exports the queried feature.")
    parser.add_argument('-f', '--source_gff', required=True, help='gff file name')
    parser.add_argument('-t', '--type', required=True, help='type of the feature')
    parser.add_argument('-a', '--attribute', required=True, help='attribute of the feature')
    parser.add_argument('-v', '--value', required=True, help='value of the attribute')
    args = parser.parse_args()

    # initialize variables
    in_fasta_section = False
    feature_found = False
    fasta_sequence = ""
    start = 0
    end = 0
    strand = "."  # specify unknown strand

    for line in open(args.source_gff):
        # check if starting to read the FASTA section
        if line.startswith("##FASTA"):
            in_fasta_section = True

        elif in_fasta_section:
            if line.startswith(">"):
                continue
            # concatenate all lines into one string
            fasta_sequence += line.rstrip()

        # search and retrieve the information of the queried feature
        else:
            # ignore command lines
            if line.startswith("#"):
                continue
            # store the data in the same line into a list
            cols = line.rstrip().split("\t")
            # search queried feature
            if len(cols) == 9 and cols[2] == args.type and re.search(r"{0}={1}".format(args.attribute, args.value),
                                                                     cols[8]):
                if not feature_found:
                    start = int(cols[3])
                    end = int(cols[4])
                    strand = cols[6]
                    feature_found = True
                else:
                    print("Warning: more than one feature matches")

    if not feature_found:
        print("Warning: no queried features found, query={0}:{1}:{2}".format(args.type, args.attribute, args.value))

    # print query and the matched sequence in FASTA format
    else:
        print(">{0}:{1}:{2}".format(args.type, args.attribute, args.value))
        if strand == "-":
            # print reverse complement
            print_sequence_width(question_4.reverse_complement(fasta_sequence[start - 1:end]))
        else:
            print_sequence_width(fasta_sequence[start - 1:end])

if __name__ == '__main__':
    main()
