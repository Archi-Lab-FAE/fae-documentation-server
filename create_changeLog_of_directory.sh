#!/usr/bin/env bash

currentDir="$(pwd)"

for entry in "$currentDir"/*;
do
  case "$entry" in
    *.md | *.mdown | *.mkdn | *.mkd | *.markdown)

      printf "### Changelog  \n" >>$entry
      printf "  \n" >> $entry
      printf "| Commit | Commit-Message | Date | Author |  \n|-------|-------|--------|--------|  \n">>$entry
      git log --pretty=format:"| %h | %s | %ad | %an |  " --date=short "$entry" >> $entry
      ;;
    *)
      echo "No MD: $entry"
      ;;
  esac
done
