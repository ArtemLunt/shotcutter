cd ..

for FOLDER in $(ls -d */ | grep -v "node_modules")
do

  cd $FOLDER;

  if test -f ".lintstagedrc.json"; then
    npm run precommit
  fi;

  cd ..;

done;
