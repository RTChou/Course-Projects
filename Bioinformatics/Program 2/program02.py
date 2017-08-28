#!/usr/bin/env python3

# encoding: utf8
# author: Renee Ti Chou

"""
This program reads in a BLASTN output file, parses the file format, retrieves
the alignment information, and prints the information of the first ten alignment
hits to the standard output in a new format.
"""

import re

def main():
    filename = "/home/rchou3/example_blast.txt"
    in_alignments_section = False
    hit_counter = 0

    with open(filename) as f:
        for line in f:
            # echo the information of the query sequence
            if line.startswith("Query=") and not in_alignments_section:
                query_id = re.search(r"Query= (\w+)", line.rstrip()).group(1)
                # search for line with the length information
                line = next(f)
                while not line.startswith("Length="):
                    line = next(f)
                query_length = re.search(r"Length=(\w+)", line.rstrip()).group(1)
                # print the information to STDOUT
                print("Query ID: {0}".format(query_id))
                print("Query Length: {0}".format(query_length))

            # check if starting to read the alignment section
            elif line.startswith("ALIGNMENTS"):
                in_alignments_section = True

            # retrieve the information of the first ten hits
            elif in_alignments_section and line.startswith(">") and hit_counter < 10:
                hit_counter += 1
                accession = re.search(r">(\w+\|.+\|)", line.rstrip()).group(1)
                # search for line with the length information
                line = next(f)
                while not line.startswith("Length="):
                    line = next(f)
                length = re.search(r"Length=(\w+)", line.rstrip()).group(1)
                # search for line with the score information
                line = next(f)
                while not re.search(r"Score", line):
                    line = next(f)
                score = re.search(r"(\w+) bits", line.rstrip()).group(1)
                # print the information to STDOUT in a new format
                print(
                    "Alignment #{0}: Accession = {1} (Length = {2}, Score = {3})".format(hit_counter, accession, length,
                                                                                         score))

if __name__ == '__main__':
    main()
