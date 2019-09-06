#########################
### build environment ###
#########################

FROM jekyll/jekyll AS builder
# set workdir
WORKDIR /app
# copy src to workdir
COPY . /app
# build static site
# permissions are not correct.
RUN chmod 777 /app/Gemfile.lock
RUN jekyll build

##################
### production ###
##################

# base image
FROM nginx:1.13.9-alpine


# copy artifact build from the 'build environment'
COPY --from=builder /app/_site /usr/share/nginx/html
COPY nginx.default.conf /etc/nginx/conf.d/default.conf

# expose port 80
EXPOSE 80

# run nginx
CMD ["nginx", "-g", "daemon off;"]
