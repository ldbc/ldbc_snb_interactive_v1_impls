FROM fedora:35

COPY umbra/ /umbra/

ENV LD_LIBRARY_PATH=/umbra/lib/

EXPOSE 5432

ENTRYPOINT ["tail"]
CMD ["-f", "/dev/null"]
