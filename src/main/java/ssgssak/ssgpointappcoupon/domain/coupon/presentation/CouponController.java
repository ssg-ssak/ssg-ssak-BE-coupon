package ssgssak.ssgpointappcoupon.domain.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappcoupon.domain.coupon.application.CouponServiceImpl;
import ssgssak.ssgpointappcoupon.domain.coupon.dto.CreateCouponInputDto;
import ssgssak.ssgpointappcoupon.domain.coupon.dto.DownloadCouponInputDto;
import ssgssak.ssgpointappcoupon.domain.coupon.dto.RegisterCouponInputDto;
import ssgssak.ssgpointappcoupon.domain.coupon.vo.CreateCouponInputVo;
import ssgssak.ssgpointappcoupon.domain.coupon.vo.DownloadCouponInputVo;
import ssgssak.ssgpointappcoupon.domain.coupon.vo.RegisterCouponInputVo;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponServiceImpl couponService;
    private final ModelMapper modelMapper;

    /*
    아래는 어드민 API
    //todo: 관리자 인증 방식 정하기
    1. 새로운 쿠폰 생성
     */

    // 1. 새로운 쿠폰 생성
    @PostMapping("/admin")
    public void createCoupon(@RequestBody CreateCouponInputVo createCouponInputVo){
        CreateCouponInputDto createCouponInputDto = modelMapper.map(
                createCouponInputVo, CreateCouponInputDto.class
        );
        couponService.createCoupon(createCouponInputDto);
    }

    /*
    아래는 유저 API
    1. 쿠폰 다운로드
    2. 쿠폰 등록
     */

    // 1. 쿠폰 다운로드(다운형 쿠폰)
    @PostMapping("/downloadable")
    public void downloadCoupon(@RequestBody DownloadCouponInputVo downloadCouponInputVo){
        DownloadCouponInputDto downloadCouponInputDto = modelMapper.map(
                downloadCouponInputVo, DownloadCouponInputDto.class);
        couponService.downloadCoupon(downloadCouponInputDto);
    }

    // 2. 쿠폰 등록(등록형 쿠폰)
    @PostMapping("/registrable")
    public void registerCoupon(@RequestBody RegisterCouponInputVo registerCouponInputVo) {
        RegisterCouponInputDto registerCouponInputDto = modelMapper.map(
                registerCouponInputVo, RegisterCouponInputDto.class);
        couponService.registerCoupon(registerCouponInputDto);
    }
}
