import * as cdk from 'aws-cdk-lib';
import {Construct} from 'constructs';
import {DefaultInstanceTenancy, Peer, Port} from "aws-cdk-lib/aws-ec2";
import {aws_iam} from "aws-cdk-lib";

// import * as sqs from 'aws-cdk-lib/aws-sqs';

export class InfrastructureStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = new cdk.aws_ec2.Vpc(this, 'vpc-automation',{
      vpcName : "vpc-automation",
      cidr : "172.31.0.0/16"
    });

    let securityGroup = new cdk.aws_ec2.SecurityGroup(this, 'security-group', {
      vpc : vpc,
      securityGroupName :'security-group',
      allowAllOutbound :  false,
    });

    securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(22));
    securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(300));

    const webRole = new cdk.aws_iam.Role(this, 'web-server-role',{
      assumedBy : new cdk.aws_iam.ServicePrincipal('ec2.amazonaws.com'),
      managedPolicies:[
          aws_iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonS3ReadOnlyAccess')
      ]
    });

    const ec2instance = new cdk.aws_ec2.Instance(this ,'ec2-instance',{
      vpc : vpc,
      vpcSubnets: {
        subnetType: cdk.aws_ec2.SubnetType.PUBLIC
      },
      role : webRole,
      securityGroup : securityGroup,
      instanceType : cdk.aws_ec2.InstanceType.of(
          cdk.aws_ec2.InstanceClass.BURSTABLE2,
          cdk.aws_ec2.InstanceSize.MICRO
      ),
      machineImage : new cdk.aws_ec2.AmazonLinuxImage({
        generation : cdk.aws_ec2.AmazonLinuxGeneration.AMAZON_LINUX_2
      }),
      keyName : "DevopsAws"

    });



  }
}
