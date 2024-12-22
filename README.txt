README
Source Code Link:
https://github.com/RobertXie1108/EECS4413FinalProj
Also submitted on eClass

SQL Scripts:
submitted on eClass

What we did to deploy with Docker (in this order):
Docker Tomcat command:
docker pull tomcat:9.0-jdk15-openjdk

Docker building the image (with the Dockerfile in GitHub/submitted on eclass, and Dockerfile needs to be in the same directory as the .war file):
docker build -t web-service .
*note "web-service" is just what I chose to name it

Making a repository:
docker tag 8c4086ed097d jasont00/web-store:1
*note the 8c4086ed097d was the image tag, jasont00 is my Docker username, and web-store is the repo name

To run it from my public repository:
Docker push to hub:
docker push jasont00/web-store:1

Run it as container:
docker run -d -p 8080:8080 web-service

Docker Pull Command:
docker pull jasont00/web-store:1

Run Docker Container:
docker run -d -p 8080:8080 jasont00/web-store:1

Docker errors or problems that can occur:
For apple silicon users (m chip) or any users who run into this error
docker: no matching manifest for linux/arm64/v8 in the manifest list entries.
Use the command below
docker pull --platform linux/amd64 jasont00/web-store:1
docker run --platform linux/amd64 -d -p 8080:8080 jasont00/web-store:1

For any users who run into this command
rosetta error: Rosetta is only intended to run on Apple Silicon with a macOS host using Virtualization.framework with Rosetta mode enabled
Use the command below
softwareupdate --install-rosetta

To Run Source Code:
import war
Run Project As

To Access the Website, use the following URL:
http://localhost:8080/EECS4413FinalProject

ADMIN Password:
12345678

How To Log In As Admin:
- go to home/catalog page
- click button in bottom right (admin?)
- enter password