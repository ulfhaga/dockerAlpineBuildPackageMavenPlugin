# dockerAlpineBuildPackageMavenPlugin

## Build

mvn -P docker clean install

mvn clean install -DskipITs

## Test

    docker run --name alpine -dit alpine sh  
    docker cp sample/target/mypackage-1.0.apk alpine:/
    docker exec -it alpine /bin/ash
    apk add --allow-untrusted mypackage-1.0.apk
    /usr/bin/hello.sh

## References

| Title      | Link |
| ----------- | ----------- |
| APKBUILD Reference               | https://wiki.alpinelinux.org/wiki/APKBUILD_Reference
| Creating an Alpine package       | https://wiki.alpinelinux.org/wiki/Creating_an_Alpine_package       |
 
 
 