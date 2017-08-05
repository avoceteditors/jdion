
CSRC = $(wildcard server/*.c)
COUT := $(patsubst server/%.c, %.o, $(wildcard server/*.c))
WEBJS = website/index.js
WEBOUT = /var/www/html/dion

CC=clang

all: python

dion-server: $(COUT)
	$(CC) -o $@ $^

$(COUT): $(CSRC)
	$(CC) -c $^

clean: 
	rm $(COUT)

clean-srv:
	rm dion-server

python: py3

py3:
	python3 setup.py install --user

docker:
	sudo docker run -p 8529:8529 -e ARANGO_ROOT_PASSWORD=openSesame arangodb/arangodb:3.2.0

web:
	cp $(WEBJS) $(WEBOUT)

