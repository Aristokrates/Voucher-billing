package org.pan.voucher.remoting;

import org.pan.voucher.remoting.model.VoucherListRequest;
import org.pan.voucher.remoting.model.VoucherListResponse;
import org.pan.voucher.remoting.model.VoucherRequest;
import org.pan.voucher.remoting.model.VoucherResponse;

public class DummyRemoteInterface implements VoucherRemoteInterface {

	@Override
	public VoucherResponse getVoucher(VoucherRequest request) {
		// TODO Auto-generated method stub
		return new VoucherResponse();
	}

	@Override
	public VoucherListResponse getVouchers(VoucherListRequest requestList) {
		// TODO Auto-generated method stub
		return new VoucherListResponse();
	}

}
