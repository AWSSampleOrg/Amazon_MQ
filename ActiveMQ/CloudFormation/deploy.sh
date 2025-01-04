#!/usr/bin/env bash
STACK_NAME="ActiveMQ"

aws cloudformation deploy \
    --template-file template.yml \
    --stack-name ${STACK_NAME} \
    --parameter-overrides \
    ProjectPrefix="" \
    VpcId="" \
    PublicSubnetAId="" \
    PublicSubnetCId="" \
    EngineVersion="5.18" \
    HostInstanceType="mq.m5.xlarge" \
    MqConfigurationId="" \
    MqConfigurationRevision=1 \
    --capabilities CAPABILITY_NAMED_IAM
