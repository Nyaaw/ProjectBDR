# Domain Name Help

## Author <!-- omit in toc -->
- Alex Berberat
- Lisa Gorgerat
- Pierric Ripoll


## Table of Contents <!-- omit in toc -->
- [Description](#description)
- [Obtain a Domain Name](#obtain-a-domain-name)
- [Add the required DNS records to the DNS zone](#add-the-required-dns-records-to-the-dns-zone)
- [Test the DNS resolution](#test-the-dns-resolution)
- [Conclusion](#conclusion)
- [Source](#source)


## Description
In this quick tutorial we will focus on how to get and set up a free Domain Name.  
If you want to use a particular Domain Name, you'll need to research for its availability and price but we will not discuss this here.  


## Obtain a Domain Name
You will need to access <http://www.duckdns.org/> and log in with your preferred method.  

> [!NOTE]
> Even though this DNS provider seems fishy and old-fashioned, it is reliable and well-known in the free domain name community.  

Click on the `Add Domain` button and choose a domain name.  


## Add the required DNS records to the DNS zone
This will allow you to access the services hosted on the virtual machine using the domain name and its subdomains you acquired.  

Add an `A` record to the DNS zone of your domain name provider to point to the IP address of the virtual machine.  

> [!NOTE] Example  
> If your domain name is `example.duckdns.org` and your virtual machine IP address is `20.73.17.105`, you must add an `A` record for `example.duckdns.org` pointing to `20.73.17.105`.  

> [!TIP]
> On Duck DNS, the default are `A`/`AAAA` records. Add a record and it will be of the right type.  

<br>

Add a second wildcard `A` record to the DNS zone of your domain name provider to point to the IP address of the virtual machine. This will allow access to all your services hosted under a subdomain of your domain name.  

> [!NOTE] Example  
> If your domain name is `example.duckdns.org` and your virtual machine IP address is `20.73.17.105`, you must add a wildcard `A` record for `*.example.duckdns.org` pointing to `20.73.17.105`.  

> [!TIP]
> On Duck DNS, only the root domain name is required. The wildcard DNS record is automatically added for you.  


## Test the DNS resolution
You can now test the DNS resolution from the virtual machine and from your local machine.  
```sh
# Test the DNS resolution
nslookup <domain name>
```  

On success, the output should be similar to the following:  
```text
Server:   127.0.0.53
Address:  127.0.0.53#53

Non-authoritative answer:
Name: example.duckdns.org
Address: 20.73.17.105
```

On failure, the output should be similar to the following:  
```text
Server:   127.0.0.53
Address:  127.0.0.53#53

** server can't find example.duckdns.org: NXDOMAIN
```

You might have to wait a few minutes for the DNS record to be propagated and get a successful response.  

Do the same for the wildcard DNS record (`*.example.duckdns.org`).


## Conclusion
You should now be able to access the virtual machine from the Internet using the domain name and its subdomains you acquired.


## Source
This quick tutorial was heavily inspired by [this course](https://github.com/heig-vd-dai-course/heig-vd-dai-course/blob/main/22-web-infrastructures/COURSE_MATERIAL.md#obtain-a-domain-name).
