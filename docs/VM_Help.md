# Virtual Machine Help

## Author <!-- omit in toc -->
- Alex Berberat
- Lisa Gorgerat
- Pierric Ripoll



## Table of Contents <!-- omit in toc -->
- [Description](#description)
- [SSH](#ssh)
- [Acquiring a virtual machine on a cloud provider](#acquiring-a-virtual-machine-on-a-cloud-provider)
  - [Create a virtual machine](#create-a-virtual-machine)
- [Access the VM using SSH](#access-the-vm-using-ssh)
  - [Recommended actions](#recommended-actions)
- [Install Docker and Docker Compose](#install-docker-and-docker-compose)
- [End](#end)
- [Source](#source)



## Description
In this quick tutorial we will focus on how to get and set up an online VM for the project.  
And will use SSH to connect to the distant VM.

> [!IMPORTANT]
> Most installation commands given in this tutorial make use of the `APT` package manager.  
> Therefore you'll need to adapt the commands if you use a different package manager.


## SSH
If you don't already have the SSH client installed you can do so with this command:
```sh
# Install the SSH client
sudo apt install openssh-client
```

Once this is done, you'll need to generate a new SSH key if you don't already have one.
You can do so with the following command:
```sh
ssh-keygen
```

## Acquiring a virtual machine on a cloud provider
Many cloud providers exist and offer free tiers. You can check the following Git repository for a list of cloud providers offering free tiers: <https://github.com/cloudcommunity/Cloud-Free-Tier-Comparison>.  
Here, we will use a virtual machine from [Microsoft Azure](https://azure.microsoft.com).  

You can access the [Azure portal](https://portal.azure.com) to create your account.  


### Create a virtual machine
You can now create a new virtual machine from the dashboard in the section `Create a resource`.

Select a virtual machine with the following characteristics:
- **Project details**
  - **Subscription**: Choose the one that fits your needs best
  - **Resource group**: Create new if needed
- **Instance details**
  - **Virtual machine name**: The name you want
  - **Region**: Choose the one that fits your needs best
  - **Availability options**: No infrastructure redundancy required
  - **Security type**: Trusted launch virtual machines (the default)
  - **Image**: Ubuntu Server 24.04 LTS - x64 Gen2 (the default)
  - **VM architecture**: x64
  - **Size**: `Standard_B1s` - you might need to click _"See all sizes"_ to see
    this option
- **Administrator account**
  - **Authentication type**: SSH public key
  - **Username**: The name you want
  - **SSH public key source**: Use existing public key
  - **SSH public key**: Paste your previously created public key here
- **Inbound port rules**
  - **Public inbound ports**: Allow selected ports
  - **Select inbound ports**: HTTP (80), HTTPS (443), SSH (22)

> [!NOTE]
> Those are only recommendation you can change them if need.  
> The only characteristic you can't change are the **Image**, **VM architecture** and **Inbound port rules** unless you adjust the API accordingly.  


Click on the `Review + create` button.  
Validate the configuration and click on the `Create` button.  
It might take a few minutes to create the virtual machine. Once the virtual machine is created, you can access it with the `Go to resource` button.  

Note the public IP address of the virtual machine. You'll need it to connect to the virtual machine with SSH.


## Access the VM using SSH
Using the public IP address of the virtual machine, you can connect to the virtual machine using SSH with the following command:
```sh
# Connect to the virtual machine with SSH
ssh ubuntu@<vm public ip>
```

The first time you connect to the virtual machine, you will be asked to confirm the fingerprint of the public key.

### Recommended actions
Once connected to the virtual machine, you can update the packages with the following command:

```sh
# Update the available packages
sudo apt update

# Upgrade the packages
sudo apt upgrade
```

You can then reboot the VM with the following command to apply all the updates:
```sh
# Reboot the VM
sudo reboot
``` 


## Install Docker and Docker Compose
You can go to the official website ([Dockerdocs](https://docs.docker.com/engine/)) and follow the instruction to install the version you need.

## End
Once this is done, you have a working VM to use for the project.


## Source
This quick tutorial was heavily inspired by [this course](https://github.com/heig-vd-dai-course/heig-vd-dai-course/blob/main/20-ssh-and-scp/COURSE_MATERIAL.md).
