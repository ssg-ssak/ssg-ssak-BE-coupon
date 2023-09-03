package ssgssak.ssgpointappcoupon.domain.coupon.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssgssak.ssgpointappcoupon.domain.coupon.dto.*;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.CouponType;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.MyCouponListData;
import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.CouponRepository;
import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.MyCouponListRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl {
    private final CouponRepository couponRepository;
    private final MyCouponListRepository myCouponListRepository;
    private final ModelMapper modelMapper;
    /*
    1. 새로운 쿠폰 생성
    2. 쿠폰 등록여부 변경(미등록 -> 등록)
     */

    // 1. 새로운 쿠폰 생성
    public void createCoupon(CreateCouponInputDto createCouponInputDto) {
        Coupon coupon = modelMapper.map(createCouponInputDto, Coupon.class);
        couponRepository.save(coupon);
    }

    // 2. 유저 쿠폰 다운로드
    public void downloadCoupon(DownloadCouponInputDto downloadCouponInputDto) {
        Coupon coupon = isCouponNumberValid(downloadCouponInputDto.getCouponNumber());
        if(coupon.getIsRegistered()){
            throw new IllegalArgumentException("이미 다운받은 쿠폰입니다.");
        } else if (coupon.getCouponType() != CouponType.DOWNLOADABLE){
            throw new IllegalArgumentException("다운로드형 쿠폰이 아닙니다.");
        }
        // 쿠폰 리스트에 쿠폰 저장
        createMyCouponListData(downloadCouponInputDto.getCouponId(), downloadCouponInputDto.getUuid());
        coupon.updateCouponStatus(); // 쿠폰 등록 여부 true로 변경
    }

    // 3. 유저 쿠폰 번호 입력하여 등록
    public void registerCoupon(RegisterCouponInputDto registerCouponInputDto) {
        Coupon coupon = isCouponNumberValid(registerCouponInputDto.getCouponNumber());
        isCouponExpired(coupon.getCouponExpirationDate());
        if(coupon.getIsRegistered()){
            throw new IllegalArgumentException("이미 등록된 쿠폰입니다.");
        } else if (coupon.getCouponType() != CouponType.REGISTRABLE){
            throw new IllegalArgumentException("등록형 쿠폰이 아닙니다.");
        }
        // 쿠폰 리스트에 쿠폰 저장
        createMyCouponListData(registerCouponInputDto.getCouponId(), registerCouponInputDto.getUuid());
        coupon.updateCouponStatus(); // 쿠폰 등록 여부 true로 변경
    }

    // 4. 쿠폰번호 유효 여부 확인
    public Coupon isCouponNumberValid(String couponNumber) {
        return couponRepository.findByCouponNumber(couponNumber).orElseThrow(
                () -> new IllegalArgumentException("쿠폰이 유효하지 않습니다.")
        );
    }

    // 5. 쿠폰 만료 여부 확인
    public void isCouponExpired(LocalDate couponExpirationDate) {
        if(couponExpirationDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }
    }

    // 6. 내 쿠폰리스트에 쿠폰 저장
    public void createMyCouponListData(Long couponId, String uuid) {
        MyCouponListData myCouponListData = MyCouponListData.builder()
                .couponId(couponId)
                .used(false)
                .uuid(uuid) //todo: 임시설정
                .build();
        myCouponListRepository.save(myCouponListData);
    }
}
