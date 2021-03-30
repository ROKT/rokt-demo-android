FROM ubuntu:20.04

# Required for Jenv
SHELL ["/bin/bash", "-c"]

## Set timezone to UTC by default
RUN ln -sf /usr/share/zoneinfo/Etc/UTC /etc/localtime

## Use unicode
RUN apt-get update && apt-get -y install locales && \
    locale-gen en_US.UTF-8 || true
ENV LANG=en_US.UTF-8

## Install dependencies
RUN apt-get update && apt-get install --no-install-recommends -y \
  openjdk-11-jdk \
  git \
  wget \
  build-essential \
  zlib1g-dev \
  libssl-dev \
  libreadline-dev \
  unzip \
  ssh \
  # Fastlane plugins dependencies
  # - fastlane-plugin-badge (curb)
  libcurl4 libcurl4-openssl-dev

## Clean dependencies
RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/*

## Install rbenv
ENV RBENV_ROOT "/root/.rbenv"
RUN git clone https://github.com/rbenv/rbenv.git $RBENV_ROOT
ENV PATH "$PATH:$RBENV_ROOT/bin"
ENV PATH "$PATH:$RBENV_ROOT/shims"

## Install jenv
ENV JENV_ROOT "$HOME/.jenv"
RUN git clone https://github.com/jenv/jenv.git $JENV_ROOT
ENV PATH "$PATH:$JENV_ROOT/bin"
RUN mkdir $JENV_ROOT/versions
ENV JDK_ROOT "/usr/lib/jvm/"
RUN jenv add ${JDK_ROOT}/java-11-openjdk-amd64
RUN echo 'export PATH="$JENV_ROOT/bin:$PATH"' >> ~/.bashrc
RUN echo 'eval "$(jenv init -)"' >> ~/.bashrc

# Install ruby-build (rbenv plugin)
RUN mkdir -p "$RBENV_ROOT"/plugins
RUN git clone https://github.com/rbenv/ruby-build.git "$RBENV_ROOT"/plugins/ruby-build

# Install ruby envs
RUN echo “install: --no-document” > ~/.gemrc
ENV RUBY_CONFIGURE_OPTS=--disable-install-doc
RUN rbenv install 2.7.1
RUN rbenv install 2.6.6

# Setup default ruby env
RUN rbenv global 2.7.1
RUN gem install bundler:2.1.4

## Install Android SDK
ARG sdk_version=commandlinetools-linux-6200805_latest.zip
ARG android_home=/opt/android/sdk
ARG android_api=android-30
ARG android_build_tools=30.0.3
ARG android_ndk=false
ARG ndk_version=21.0.6113669
ARG cmake=3.10.2.4988404
RUN mkdir -p ${android_home} && \
    wget --quiet --output-document=/tmp/${sdk_version} https://dl.google.com/android/repository/${sdk_version} && \
    unzip -q /tmp/${sdk_version} -d ${android_home} && \
    rm /tmp/${sdk_version}

# Set environmental variables
ENV ANDROID_HOME ${android_home}
ENV PATH=${ANDROID_HOME}/emulator:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools:${PATH}

RUN mkdir ~/.android && echo '### User Sources for Android SDK Manager' > ~/.android/repositories.cfg

RUN yes | sdkmanager --sdk_root=$ANDROID_HOME --licenses
RUN sdkmanager --sdk_root=$ANDROID_HOME --install \
  "platform-tools" \
  "build-tools;${android_build_tools}" \
  "platforms;${android_api}"