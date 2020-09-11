## Unit tests 
mvn test

## Integration test 
mvn verify

## Integration test without server

The server needs first to start manually. Read the document README.md in the submodule server ( ../server).

mvn -D docker.skip verify



