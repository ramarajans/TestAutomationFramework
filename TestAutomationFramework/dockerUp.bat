docker run -d -p 4444:4444 --name selenium-hub selenium/hub
docker run -d -p 5900:5900 --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-chrome-debug
docker run -d -p 5901:5900 --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-firefox-debug