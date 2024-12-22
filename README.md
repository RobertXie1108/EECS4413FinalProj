# Quick start guide
## For docker users
#### Docker pull command:
```
docker pull jasont00/web-store:1
```
#### Run Docker Container:
```
docker run -d -p 8080:8080 jasont00/web-store:1
```
### For apple silicon users (m chip) or any users who run into this error docker
no matching manifest for linux/arm64/v8 in the manifest list entries.
### Use the command below
```
docker pull --platform linux/amd64 jasont00/web-store:1
docker run --platform linux/amd64 -d -p 8080:8080 jasont00/web-store:1
```
### For any users who run into this error
rosetta error: Rosetta is only intended to run on Apple Silicon with a macOS host using Virtualization.framework with Rosetta mode enabled
#### Use the command below
```
softwareupdate --install-rosetta
```
