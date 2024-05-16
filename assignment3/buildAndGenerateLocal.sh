./buildAndGenerateGlobal.sh
echo "Deploying and decompressing execution environments."
mkdir -p /home/$USER/test/GameOfRope
rm -rf /home/$USER/test/GameOfRope/*
cp dirRegistry.zip /home/$USER/test/SleepingBarbers
cp dirGeneralRepos.zip /home/$USER/test/GameOfRope
cp dirRefereeSite.zip /home/$USER/test/GameOfRope
cp dirPlayground.zip /home/$USER/test/GameOfRope
cp dirContestantsBench.zip /home/$USER/test/GameOfRope
cp dirReferee.zip /home/$USER/test/GameOfRope
cp dirCoaches.zip /home/$USER/test/GameOfRope
cp dirContestants.zip /home/$USER/test/GameOfRope
cd /home/$USER/test/GameOfRope
mv set_rmiregistry_alt.sh /home/$USER
unzip -q dirGeneralRepos.zip
unzip -q dirRefereeSite.zip
unzip -q dirPlayground.zip
unzip -q dirContestantsBench.zip
unzip -q dirReferee.zip
unzip -q dirCoaches.zip
unzip -q dirContestants.zip
