package ssgssak.ssgpointappcoupon.domain.coupon.application;

import ssgssak.ssgpointappcoupon.domain.coupon.dto.*;

public interface CouponService {
    // 1. 새로운 쿠폰 생성
    public CreateCouponOutputDto createCoupon(CreateCouponInputDto dto);

    // 2. 개별 쿠폰(쿠폰 데이터 생성) 생성
    public CreateIndividualCouponOutputDto createIndividualCoupon(CreateIndividualCouponInputDto dto);

    // 3. 쿠폰 전체 조회하기(현재 시간 기준으로 만료되지 않은 쿠폰 조회하기)
    public GetAllCouponsOutputDto getAllCoupons(String uuid);

    // 4. 쿠폰 다운로드(다운로드 버튼 클릭)
    public DownloadCouponOutputDto downloadCoupon(DownloadCouponInputDto dto, String uuid);

    // 5. 쿠폰 등록하기(쿠폰 번호를 입력하여)
    public RegisterCouponOutputDto registerCoupon(RegisterCouponInputDto dto, String uuid);

    // 6. 쿠폰 전체 다운로드(쿠폰 페이지에서 다운 받지 않은 쿠폰만)
    public DownloadAllCouponsOutputDto downloadAllCoupons(DownloadAllCouponsInputDto dto, String uuid);

    // 7. 쿠폰 조회하기(쿠폰 사용하기 버튼 클릭)
    public GetCouponOutputDto getCoupon(GetCouponInputDto dto, String uuid);

    // 8. 소유하고 사용가능한 쿠폰 전체 조회하기(마이 쿠폰함에서)
    public GetMyCouponsOutputDto getMyCoupons(GetMyCouponsInputDto dto, String uuid);

    // 9. 사용완료/기간만료 쿠폰 전체 조회하기(마이 쿠폰함에서)
    public GetUnavailableCouponsOutputDto getUnavailableCoupons(GetUnavailableCouponsInputDto dto, String uuid);
}