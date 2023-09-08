package ssgssak.ssgpointappcoupon.domain.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssgssak.ssgpointappcoupon.domain.coupon.application.CouponServiceImpl;
import ssgssak.ssgpointappcoupon.domain.coupon.dto.*;
import ssgssak.ssgpointappcoupon.domain.coupon.vo.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponServiceImpl couponService;
    private final ModelMapper modelMapper;

    /*
    아래는 어드민 API
    //todo: 관리자 인증 방식 정하기
    1. 새로운 쿠폰 생성
    2. 개별 쿠폰 생성
     */

    // 1. 새로운 쿠폰 생성
    @PostMapping("/admin")
    public ResponseEntity<CreateCouponOutputVo> createCoupon(@RequestBody CreateCouponInputVo createCouponInputVo){
        CreateCouponInputDto createCouponInputDto = modelMapper.map(
                createCouponInputVo, CreateCouponInputDto.class
        );
        CreateCouponOutputDto createCouponOutputDto = couponService.createCoupon(createCouponInputDto);
        CreateCouponOutputVo createCouponOutputVo = modelMapper.map(
                createCouponOutputDto, CreateCouponOutputVo.class
        );
        return new ResponseEntity<>(createCouponOutputVo, HttpStatus.OK);
    }

    // 2. 개별 쿠폰 생성
    @PostMapping("/admin/individual")
    public ResponseEntity<CreateIndividualCouponOutputVo> createIndividualCoupon(
            @RequestBody CreateIndividualCouponInputVo createIndividualCouponInputVo) {
        CreateIndividualCouponInputDto createIndividualCouponInputDto = modelMapper.map(
                createIndividualCouponInputVo, CreateIndividualCouponInputDto.class);
        CreateIndividualCouponOutputDto createIndividualCouponOutputDto =
                couponService.createIndividualCoupon(createIndividualCouponInputDto);
        CreateIndividualCouponOutputVo createIndividualCouponOutputVo = modelMapper.map(
                createIndividualCouponOutputDto, CreateIndividualCouponOutputVo.class);
        return new ResponseEntity<>(createIndividualCouponOutputVo, HttpStatus.OK);
    }

    /*
    아래는 유저 API
    1. 쿠폰 전체 조회(쿠폰 페이지 방문)
    2. 쿠폰 전체 다운로드(다운 받지 않은 쿠폰만)
    3. 쿠폰 다운로드
    4. 쿠폰 등록하기
    5. 쿠폰 조회하기(쿠폰 사용하기 버튼 클릭)
    6. 소유하고 사용가능한 쿠폰 전체 조회하기(마이 쿠폰함에서)
    7. 사용완료/기간만료 쿠폰 전체 조회하기(마이 쿠폰함에서)
     */

    // 1. 쿠폰 전체 조회(쿠폰 페이지 방문)
    @GetMapping("/downloadable/all-coupons")
    public ResponseEntity<GetAllCouponsOutputVo> getAllCoupons() {
        GetAllCouponsOutputDto getAllCouponsOutputDto = couponService.getAllCoupons();
        GetAllCouponsOutputVo getAllCouponsOutputVo = modelMapper.map(
                getAllCouponsOutputDto, GetAllCouponsOutputVo.class);
        return new ResponseEntity<>(getAllCouponsOutputVo, HttpStatus.OK);
    }

    // 2. 쿠폰 전체 다운로드(쿠폰 페이지에서 다운 받지 않은 쿠폰만)
    /*
    쿠폰 전체 조회에서 isRegistered가 false인 쿠폰만 다운로드 버튼을 보여준다.
    따라서 프론트 단에서 !isRegistered인 쿠폰의 couponId를 InputVo로 받아온다.
     */
    @PostMapping("/downloadable/all-coupons")
    public ResponseEntity<DownloadAllCouponsOutputVo> downloadAllCoupons(
            @RequestBody DownloadAllCouponsInputVo downloadAllCouponsInputVo) {
        DownloadAllCouponsInputDto downloadAllCouponsInputDto = modelMapper.map(
                downloadAllCouponsInputVo, DownloadAllCouponsInputDto.class);
        DownloadAllCouponsOutputDto downloadAllCouponsOutputDto =
                couponService.downloadAllCoupons(downloadAllCouponsInputDto);
        DownloadAllCouponsOutputVo downloadAllCouponsOutputVo = modelMapper.map(
                downloadAllCouponsOutputDto, DownloadAllCouponsOutputVo.class);
        return new ResponseEntity<>(downloadAllCouponsOutputVo, HttpStatus.OK);
    }
    // 3. 쿠폰 다운로드(다운로드형 쿠폰만 다운로드 가능)
    @PostMapping("/downloadable")
    public ResponseEntity<DownloadCouponOutputVo> downloadCoupon(
            @RequestBody DownloadCouponInputVo downloadCouponInputVo) {
        DownloadCouponInputDto downloadCouponInputDto = modelMapper.map(
                downloadCouponInputVo, DownloadCouponInputDto.class);
        DownloadCouponOutputDto downloadCouponOutputDto = couponService.downloadCoupon(downloadCouponInputDto);
        DownloadCouponOutputVo downloadCouponOutputVo = modelMapper.map(
                downloadCouponOutputDto, DownloadCouponOutputVo.class);
        return new ResponseEntity<>(downloadCouponOutputVo, HttpStatus.OK);
    }

    // 4. 쿠폰 등록하기(등록형 쿠폰의 couponNumber를 입력하여 다운로드)
    @PostMapping("/registrable")
    public ResponseEntity<RegisterCouponOutputVo> registerCoupon(
            @RequestBody RegisterCouponInputVo registerCouponInputVo) {
        RegisterCouponInputDto registerCouponInputDto = modelMapper.map(
                registerCouponInputVo, RegisterCouponInputDto.class);
        RegisterCouponOutputDto registerCouponOutputDto = couponService.registerCoupon(registerCouponInputDto);
        RegisterCouponOutputVo registerCouponOutputVo = modelMapper.map(
                registerCouponOutputDto, RegisterCouponOutputVo.class);
        return new ResponseEntity<>(registerCouponOutputVo, HttpStatus.OK);
    }

    // 5. 쿠폰 조회하기(쿠폰 사용하기 버튼 클릭)
    /*
    프론트 쪽에서 조회하는 쿠폰의 couponId를 InputVo로 받아온다.
     */
    @GetMapping("")
    public ResponseEntity<GetCouponOutputVo> getCoupon(GetCouponInputVo getCouponInputVo) {
        GetCouponInputDto getCouponInputDto = modelMapper.map(getCouponInputVo, GetCouponInputDto.class);
        GetCouponOutputDto getCouponOutputDto = couponService.getCoupon(getCouponInputDto);
        GetCouponOutputVo getCouponOutputVo = modelMapper.map(getCouponOutputDto, GetCouponOutputVo.class);
        return new ResponseEntity<>(getCouponOutputVo, HttpStatus.OK);
    }

    // 6. 소유하고 사용가능한 쿠폰 전체 조회하기(마이 쿠폰함에서)
    @GetMapping("/my-coupons")
    public ResponseEntity<GetMyCouponsOutputVo> getMyCoupons(GetMyCouponsInputVo getMyCouponsInputVo) {
        GetMyCouponsInputDto getMyCouponsInputDto = modelMapper.map(getMyCouponsInputVo, GetMyCouponsInputDto.class);
        GetMyCouponsOutputDto getMyCouponsOutputDto = couponService.getMyCoupons(getMyCouponsInputDto);
        GetMyCouponsOutputVo getMyCouponsOutputVo = modelMapper.map(getMyCouponsOutputDto, GetMyCouponsOutputVo.class);
        return new ResponseEntity<>(getMyCouponsOutputVo, HttpStatus.OK);
    }

    // 7. 사용완료/기간만료 쿠폰 전체 조회하기(마이 쿠폰함에서)
    @GetMapping("/my-coupons/unavailable")
    public ResponseEntity<GetUnavailableCouponsOutputVo> getUnavailableCoupons(
            GetUnavailableCouponsInputVo getUnavailableCouponsInputVo) {
        GetUnavailableCouponsInputDto getUnavailableCouponsInputDto = modelMapper.map(
                getUnavailableCouponsInputVo, GetUnavailableCouponsInputDto.class);
        GetUnavailableCouponsOutputDto getUnavailableCouponsOutputDto =
                couponService.getUnavailableCoupons(getUnavailableCouponsInputDto);
        GetUnavailableCouponsOutputVo getUnavailableCouponsOutputVo = modelMapper.map(
                getUnavailableCouponsOutputDto, GetUnavailableCouponsOutputVo.class);
        return new ResponseEntity<>(getUnavailableCouponsOutputVo, HttpStatus.OK);
    }
}