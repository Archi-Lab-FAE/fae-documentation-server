#!/usr/bin/env bash

currentDir="$(pwd)"

for entry in "$currentDir"/*;
do
  case "$entry" in
    *.md | *.mdown | *.mkdn | *.mkd | *.markdown)
      printf "\n" >> $entry
      printf "### ChangeLog\n" >> $entry
      printf "| Commit | Commit-Message | Datum | Nutzer |\n" >> $entry
      printf "|-------|-------|--------|--------|\n" >> $entry
      git log --pretty=format:"| %h | %s | %ad | %an | " --date=short "$entry" >> $entry
      printf "\n"
      ;;
    *)
      echo "No MD: $entry"
      ;;
  esac
done
