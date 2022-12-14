#!/usr/bin/perl
#-------------------------------------------------------------------------------
# Compile, test and upload the Ashley class
# Philip R Brenan at appaapps dot com, Appa Apps Ltd Inc., 2022
#-------------------------------------------------------------------------------
use v5.30;
use warnings FATAL => qw(all);
use strict;
use Carp;
use Data::Dump qw(dump);
use Data::Table::Text qw(:all);
use GitHub::Crud qw(:all);
use feature qw(say current_sub);

my $home = currentDirectory;                                                    # Local files
my $user = q(philiprbrenan);                                                    # User
my $repo = q(Ashley);                                                           # Repo
my $wf   = q(.github/workflows/main.yml);                                       # Work flow on Ubuntu

my $j = qq($repo.java);                                                         # Files
my $t = qq(Test$repo.java);
my $c = q(./Classes/);
my $p = q(pushToGitHub.pl);
my $r = q(README.md);

my ($cm, $cc, $cj, $cz) = my @c = map {s(#.*\Z) ()sr} split /\n/, <<END;        # Commands
mkdir -p $c
javac -d $c -cp $c         $j
java                -cp $c -ea $t
END

for my $c(@c)                                                                   # Say and execute the commands
 {say STDERR $c;
  say STDERR qx($c);
 }

for my $s($j, $t, $r, q(pushToGitHub.pl))                                       # Upload each selected file
 {my $p = readFile($s);
  my $w = writeFileUsingSavedToken($user, $repo, $s, $p);                       # Write file to github
  lll "$w $s";
 }

my $d = dateTimeStamp;                                                          # Make work flow different
my $y = <<END;                                                                  # Create work flow
# Test $d

name: Test

on:
  push

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout\@v2
      with:
        ref: 'main'

    - name: mkdir
      run: |
        $cm

    - name: Compile all Java files belonging to the package
      run: |
        $cc

    - name: Java - execute the test program using the package
      run: |
        $cj

    - name: Tree - show the resulting file structure
      run: |
        tree
END

lll "Ubuntu work flow for $repo ", writeFileUsingSavedToken($user, $repo, $wf, $y);
