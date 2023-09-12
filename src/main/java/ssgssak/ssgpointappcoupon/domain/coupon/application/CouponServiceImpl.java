package ssgssak.ssgpointappcoupon.domain.coupon.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappcoupon.domain.coupon.dto.*;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.CouponType;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.IndividualCoupon;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.UserCouponList;
import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.CouponRepository;
import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.IndividualCouponRepository;
import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.UserCouponListRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final IndividualCouponRepository individualCouponRepository;
    private final UserCouponListRepository userCouponListRepository;
    private final ModelMapper modelMapper;
    private final LocalDate today = LocalDate.now();
    /*
    1. 새로운 쿠폰 생성
    2. 개별 쿠폰(쿠폰 데이터 생성) 생성
    3. 쿠폰 전체 조회하기(현재 시간 기준으로 만료되지 않은 쿠폰 조회하기)
    4. 쿠폰 다운로드(다운로드 버튼 클릭)
    5. 쿠폰 등록하기(쿠폰 번호를 입력하여)
    6. 전체 쿠폰 다운로드
    7. 쿠폰 조회하기(쿠폰 사용하기 버튼 클릭)
    8. 소유하고 사용가능한 쿠폰 전체 조회하기(마이 쿠폰함에서)
    9. 사용완료/기간만료 쿠폰 전체 조회하기(마이 쿠폰함에서)
     */

    // 1. 새로운 쿠폰 생성
    @Override
    public CreateCouponOutputDto createCoupon(CreateCouponInputDto createCouponInputDto) {
        Coupon coupon = modelMapper.map(createCouponInputDto, Coupon.class);
        couponRepository.save(coupon);
        return modelMapper.map(coupon, CreateCouponOutputDto.class);
    }

    // 2. 개별 쿠폰(쿠폰 데이터 생성) 생성
    @Override
    public CreateIndividualCouponOutputDto createIndividualCoupon(
            CreateIndividualCouponInputDto createIndividualCouponInputDto) {
        couponRepository.findById(createIndividualCouponInputDto.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 생성할 수 없습니다."));
        /*
        쿠폰번호/바코드 Url 생성
        쿠폰번호와 바코드 생성은 외부 API를 사용하여 생성해야할 것 같음.
         */
        String generatedCouponNumber = couponNumberGenerator();
        String generatedBarcodeUrl = barcodeUrlGenerator(generatedCouponNumber);
        // 저장할 개별 쿠폰 레코드 생성
        IndividualCoupon individualCoupon = IndividualCoupon.builder()
                .couponId(createIndividualCouponInputDto.getCouponId())
                .couponNumber(generatedCouponNumber)
                .barcodeUrl(generatedBarcodeUrl)
                .isRegistered(false)
                .build();
        individualCouponRepository.save(individualCoupon);
        return modelMapper.map(individualCoupon, CreateIndividualCouponOutputDto.class);
    }

    // 2-1. 쿠폰번호 생성 메서드
    // todo: 로직 생각해보기
    public String couponNumberGenerator() {
        Random random = new Random();
        Long randomNumber = random.nextLong(Long.MAX_VALUE);
        return Long.toString(randomNumber);
    }

    // 2-2. 바코드 Url 생성 메서드
    // todo: 로직 생각해보기
    public String barcodeUrlGenerator(String generatedCouponNumber) {
        return "BarcodeUrl.com/" + generatedCouponNumber;
    }

    // 3. 쿠폰 전체 조회하기(현재 시간 기준으로 만료되지 않은 쿠폰 조회하기)
    @Override
    public GetAllCouponsOutputDto getAllCoupons(String uuid) {
        List<Coupon> coupons =
                couponRepository.findAllByTypeAndStartDateLessThanEqualAndExpirationDateGreaterThanEqual(
                        CouponType.DOWNLOADABLE, today, today);
        if(coupons.isEmpty()) {
            throw new IllegalArgumentException("쿠폰이 존재하지 않습니다.");
        }
        ArrayList<HashMap<String, String>> couponArrayList = new ArrayList<>();
        HashMap<String, String> couponHashMap;
        for(Coupon coupon : coupons) {
            couponHashMap = new HashMap<>();
            couponHashMap.put("id", coupon.getId().toString());
            couponHashMap.put("startDate", coupon.getStartDate().toString());
            couponHashMap.put("expirationDate", coupon.getExpirationDate().toString());
            couponHashMap.put("name", coupon.getName());
            couponHashMap.put("brand", coupon.getBrand());
            couponHashMap.put("discountImageUrl", coupon.getDiscountImageUrl());
            couponHashMap.put("brandImageUrl", coupon.getBrandImageUrl());
            couponHashMap.put("brandLogoUrl", coupon.getBrandLogoUrl());
            couponHashMap.put("type", coupon.getType().toString());

            if(null == userCouponListRepository.findByUuidAndCouponId(uuid, coupon.getId())) {
                couponHashMap.put("isRegistered", "false");
            } else {
                couponHashMap.put("isRegistered", "true");
            }
            couponArrayList.add(couponHashMap);
        }
        return GetAllCouponsOutputDto.builder()
                .coupons(couponArrayList)
                .build();
    }

    // 4. 쿠폰 다운로드(다운로드 버튼 클릭)
    @Override
    public DownloadCouponOutputDto downloadCoupon(DownloadCouponInputDto downloadCouponInputDto, String uuid) {
        IndividualCoupon individualCoupon = individualCouponRepository.findFirstByCouponIdAndIsRegisteredFalse(
                downloadCouponInputDto.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 모두 소진되었습니다."));
        // 쿠폰 업데이트(개별 쿠폰 유저에게 귀속 처리, 유저 uuid 필드 값 등록)
        updateIndividualCoupon(individualCoupon, uuid);
        // 마이쿠폰함에 들어갈 레코드 생성
        UserCouponList userCouponList = UserCouponList.builder()
                .couponId(individualCoupon.getCouponId())
                .uuid(uuid)
                .downloadDate(LocalDate.now())
                .isUsed(false)
                .isExpired(false)
                .build();
        userCouponListRepository.save(userCouponList);
        return DownloadCouponOutputDto.builder()
                .id(userCouponList.getId())
                .couponId(userCouponList.getCouponId())
                .downloadDate(userCouponList.getDownloadDate())
                .isUsed(userCouponList.getIsUsed())
                .isExpired(userCouponList.getIsExpired())
                .build();
    }

    // 4-1. 개별 쿠폰 귀속 처리, 유저 uuid 등록
    public void updateIndividualCoupon(IndividualCoupon individualCoupon, String uuid) {
        individualCoupon.updateUuid(uuid);
        individualCoupon.updateIsRegisteredToTrue();
    }

    // 5. 쿠폰 등록하기(쿠폰 번호를 입력하여)
    /*
    예외 상황:
    1. 쿠폰 번호 유효 여부 ✅
    2. 쿠폰 번호 등록 여부 ✅
    3. 등록형 쿠폰 여부 ✅
    4. 쿠폰 만료 여부 //todo: 쿠폰 만료 여부 매일 00시에 확인하여 DB 자동 업데이트 하는 로직 생각해보기
     */
    @Override
    public RegisterCouponOutputDto registerCoupon(RegisterCouponInputDto registerCouponInputDto, String uuid) {
        // 1. 쿠폰 번호 유효 여부 확인, 2. 쿠폰 등록 여부 확인
        IndividualCoupon individualCoupon =
                individualCouponRepository.findFirstByCouponNumberAndIsRegisteredFalse(
                        registerCouponInputDto.getCouponNumber())
                        .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
        // 3. 등록형 쿠폰 여부 확인, 4. 쿠폰 만료 여부 확인
        couponRepository.findFirstByIdAndTypeAndStartDateLessThanEqualAndExpirationDateGreaterThanEqual(
               individualCoupon.getCouponId(), CouponType.REGISTRABLE, today, today)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
        // 쿠폰 업데이트(개별 쿠폰 유저에게 귀속 처리, 유저 uuid 필드 값 등록)
        updateIndividualCoupon(individualCoupon, uuid);
        // 마이쿠폰함에 들어갈 레코드 생성
        UserCouponList userCouponList = UserCouponList.builder()
                .couponId(individualCoupon.getCouponId())
                .uuid(uuid)
                .downloadDate(LocalDate.now())
                .isUsed(false)
                .isExpired(false)
                .build();
        userCouponListRepository.save(userCouponList);
        return RegisterCouponOutputDto.builder()
                .id(userCouponList.getId())
                .couponId(userCouponList.getCouponId())
                .downloadDate(userCouponList.getDownloadDate())
                .isUsed(userCouponList.getIsUsed())
                .isExpired(userCouponList.getIsExpired())
                .build();
    }

    // 6. 쿠폰 전체 다운로드(쿠폰 페이지에서 다운 받지 않은 쿠폰만)
    @Override
    public DownloadAllCouponsOutputDto downloadAllCoupons(
            DownloadAllCouponsInputDto downloadAllCouponsInputDto, String uuid) {
        List<HashMap<String, String>> couponIdList;
        couponIdList = downloadAllCouponsInputDto.getCouponIdList();
        ArrayList<UserCouponList> downloadedCoupons = new ArrayList<>();
        for(HashMap<String, String> couponIdHash : couponIdList) {
            Long couponId = Long.parseLong(couponIdHash.get("id"));
            IndividualCoupon individualCoupon =
                    individualCouponRepository.findFirstByCouponIdAndIsRegisteredFalse(couponId)
                            .orElseThrow(() -> new IllegalArgumentException("쿠폰을 다운받을 수 없습니다."));
            // 쿠폰 데이터에 귀속 여부 true로 변경, 쿠폰 소유하는 유저 uuid 등록
            updateIndividualCoupon(individualCoupon, uuid);
            UserCouponList userCouponList = UserCouponList.builder()
                    .couponId(individualCoupon.getCouponId())
                    .uuid(uuid)
                    .downloadDate(LocalDate.now())
                    .isUsed(false)
                    .isExpired(false)
                    .build();
            // 유저 쿠폰함에 쿠폰 데이터 추가
            userCouponListRepository.save(userCouponList);
            downloadedCoupons.add(userCouponList.toBuilder().uuid(null).build());
        }
        return DownloadAllCouponsOutputDto.builder()
                .downloadedCoupons(downloadedCoupons)
                .build();
    }

    // 7. 쿠폰 조회하기(쿠폰 사용하기 버튼 클릭)
    @Override
    public GetCouponOutputDto getCoupon(GetCouponInputDto getCouponInputDto, String uuid) {
        Long couponId = Long.parseLong(getCouponInputDto.getCouponId());
        IndividualCoupon individualCoupon =
                individualCouponRepository.findByCouponIdAndUuidAndIsRegisteredTrue(
                        couponId, uuid)
                        .orElseThrow(() -> new IllegalArgumentException("쿠폰을 불러올 수 없습니다."));
        return GetCouponOutputDto.builder()
                .barcodeUrl(individualCoupon.getBarcodeUrl())
                .couponNumber(individualCoupon.getCouponNumber())
                .build();
    }

    // 8. 소유하고 사용가능한 쿠폰 전체 조회하기(마이 쿠폰함에서)
    @Override
    public GetMyCouponsOutputDto getMyCoupons(GetMyCouponsInputDto getMyCouponsInputDto, String uuid) {
        // 유저가 소유한 쿠폰 데이터에서 해당 유저의 uuid로 userCouponListData로 불러오기
        // (사용하지 않고/만료되지 않은 유효한 쿠폰만)
        List<UserCouponList> userCouponListData =
                userCouponListRepository.findAllByUuidAndIsUsedFalseAndIsExpiredFalse(uuid);
        // 페이지에 전달할 데이터를 담는 ArrayList
        ArrayList<Coupon> myCoupons = new ArrayList<>();
        Coupon coupon;
        // ArrayList에 쿠폰 데이터 담기
        for(UserCouponList userCouponData : userCouponListData) {
            coupon = couponRepository.findById(userCouponData.getCouponId())
                    .orElseThrow(() -> new IllegalArgumentException("쿠폰을 불러올 수 없습니다."));
            myCoupons.add(coupon);
        }
        // 최신순 정렬(Coupon의 startDate를 Descending으로 정렬)
        if(getMyCouponsInputDto.getOrder().equals("new")) {
            myCoupons.sort((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate()));
        }
        // 마감임박 순으로 정렬(Coupon의 expirationDate를 Ascending으로 정렬)
        else {
            myCoupons.sort((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()));
        }
        // 정렬된 쿠폰 리스트를 Dto로 변환하여 리턴
        return GetMyCouponsOutputDto.builder()
                .myCoupons(myCoupons)
                .build();
    }

    // 9. 사용완료/기간만료 쿠폰 전체 조회하기(마이 쿠폰함에서)
    @Override
    public GetUnavailableCouponsOutputDto getUnavailableCoupons(
            GetUnavailableCouponsInputDto getUnavailableCouponsInputDto, String uuid) {
        List<UserCouponList> unavailableUserCouponList;
        String couponStatus = getUnavailableCouponsInputDto.getStatus();
        // 사용 불가능한 전체 쿠폰 조회
        if(couponStatus.isEmpty() || couponStatus.equals("all")){
            unavailableUserCouponList = userCouponListRepository.findAllByUuidAndIsUsedTrueOrIsExpiredTrue(uuid);
        }
        // 만료 쿠폰 조회
        else if(couponStatus.equals("isExpired")) {
            unavailableUserCouponList = userCouponListRepository.findAllByUuidAndIsExpiredTrue(uuid);
        }
        // 사용 완료 쿠폰 조회
        else {
            unavailableUserCouponList = userCouponListRepository.findAllByUuidAndIsUsedTrue(uuid);
        }

        // 페이지에 전달할 쿠폰 데이터를 담는 ArrayList
        ArrayList<Coupon> unavailableCouponList = new ArrayList<>();
        Coupon coupon;
        for(UserCouponList userCouponData : unavailableUserCouponList) {
            coupon = couponRepository.findById(userCouponData.getCouponId())
                    .orElseThrow(() -> new IllegalArgumentException("쿠폰을 불러올 수 없습니다."));
            unavailableCouponList.add(coupon);
        }
        return GetUnavailableCouponsOutputDto.builder()
                .unavailableCoupons(unavailableCouponList)
                .build();
    }
}
