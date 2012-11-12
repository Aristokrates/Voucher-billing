package org.pan.voucher.remoting.impl;

import java.util.List;
import java.util.UUID;

import org.pan.voucher.exception.VoucherNotFoundException;
import org.pan.voucher.remoting.VoucherRemoteInterface;
import org.pan.voucher.remoting.VoucherRemoteService;
import org.pan.voucher.remoting.model.Voucher;
import org.pan.voucher.remoting.model.VoucherListRequest;
import org.pan.voucher.remoting.model.VoucherListResponse;
import org.pan.voucher.remoting.model.VoucherRequest;
import org.pan.voucher.remoting.model.VoucherResponse;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;

public class VoucherRemoteServiceImpl implements VoucherRemoteService {
	
	@Value("${service.usernumber}")  
	private Integer serviceUserNumber;
	
	@Value("${service.password}") 
	private String servicePassword;
	
	private VoucherRemoteInterface remoteInterface;

	@Override
	public Voucher getVoucher(String networkId, Integer sellValue) throws VoucherNotFoundException {
		
		VoucherRequest request = new VoucherRequest();
		request.setUser(serviceUserNumber);
		request.setPass(servicePassword);
		request.setRefno(UUID.randomUUID().toString());
		
		request.setNetwork(networkId);
		request.setSellvalue(sellValue);
		VoucherResponse response = remoteInterface.getVoucher(request);
		
		if (response.getStatus() == 0) {
			throw new VoucherNotFoundException(response.getMessage());
		}
		
		return new Voucher(response.getPin(), response.getSerial(), response.getCostprice());
	}

	@Override
	public List<Voucher> getVouchers(String network, Integer voucherId, Integer count) throws VoucherNotFoundException {
		
		VoucherListRequest listRequest = new VoucherListRequest();
		listRequest.setUser(serviceUserNumber);
		listRequest.setPass(servicePassword);
		listRequest.setRefno(UUID.randomUUID().toString());
		
		listRequest.setNetwork(network);
		listRequest.setSellvalue(voucherId);
		listRequest.setCount(count);
		
		VoucherListResponse response = remoteInterface.getVouchers(listRequest);
		
		if (response.getStatus() == 0) {
			throw new VoucherNotFoundException(response.getMessage());
		}
		
		return response.getVouchers();
	}

	@Required
	public void setRemoteInterface(VoucherRemoteInterface remoteInterface) {
		this.remoteInterface = remoteInterface;
	}

	public Integer getServiceUserNumber() {
		return serviceUserNumber;
	}

	public void setServiceUserNumber(Integer serviceUserNumber) {
		this.serviceUserNumber = serviceUserNumber;
	}

	public String getServicePassword() {
		return servicePassword;
	}

	public void setServicePassword(String servicePassword) {
		this.servicePassword = servicePassword;
	}
	
}
