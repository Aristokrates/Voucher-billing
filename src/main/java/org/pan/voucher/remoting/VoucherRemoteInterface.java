package org.pan.voucher.remoting;

import org.pan.voucher.remoting.model.VoucherListRequest;
import org.pan.voucher.remoting.model.VoucherListResponse;
import org.pan.voucher.remoting.model.VoucherRequest;
import org.pan.voucher.remoting.model.VoucherResponse;

public interface VoucherRemoteInterface {
	
	VoucherResponse getVoucher(VoucherRequest request);
	
	VoucherListResponse getVouchers(VoucherListRequest requestList);

}
