# !/usr/bin/env bash
for file in ./*
do
	if [ ${#file} -gt '37' ];
	then curl -F "id=${file:2:32}" -F "bookPicture=@/home/zhaoshenglong/bookstore/book/${file:2:36}" http://localhost:8080/api/public/img/upload ;
	fi
done
