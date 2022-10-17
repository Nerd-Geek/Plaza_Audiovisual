FROM yobasystems/alpine-mariadb:10
#FROM yobasystems/alpine-postgres

ENV MYSQL_ROOT_PASSWORD plaza123
ENV MYSQL_USER user
ENV MYSQL_PASSWORD plaza123
ENV MYSQL_DATABASE plazaAudio

COPY ./sql /docker-entrypoint-initdb.d/