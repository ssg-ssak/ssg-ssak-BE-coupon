//package ssgssak.ssgpointappcoupon.domain.coupon.application;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;
//import ssgssak.ssgpointappcoupon.domain.coupon.entity.UserCouponList;
//import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.CouponRepository;
//import ssgssak.ssgpointappcoupon.domain.coupon.infrastructure.UserCouponListRepository;
//import ssgssak.ssgpointappevent.domain.eventlist.entity.EventList;
//import ssgssak.ssgpointappevent.domain.eventlist.infrastructure.EventListRepository;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@Transactional
//public class SchedulerTask {
//    //현재일이 종료일을 넘어갈 경우 자동으로 종료처리(매일 00시에 메서드 자동 동작)
//    //날짜 바뀔 때마다 쿠폰 만료여부 업데이트
//    private final UserCouponListRepository userCouponListRepository;
//    private final CouponRepository couponRepository;
//
//    @Scheduled(cron = "0 0 * * * *") // 매일 00시에 실행
//    public void checkCouponExpirationDate() {
//        LocalDate today = LocalDate.now();
//        List<UserCouponList> validUserCoupons = userCouponListRepository.findAllByIsExpired(false);
//        HashSet<Long> expiredCouponIds = new HashSet<>();
//        expiredCouponIds = couponRepository.findAllBy
//        for(UserCouponList coupon : validCoupons) { // 현재일과 마감일 비교해서 현재일이 마감일보다 크면 isExpired를 true로 변경
//            Long couponId = coupon.getCouponId();
//            expirationDateMap.getOrDefault(couponId, couponRepository.findById(couponId).get().getExpirationDate());
//            if(coupon.getEndDate().isBefore(today)) {
//                coupon.updateIsOverToTrue();
//            }
//            else { // 디데이 업데이트
//                event.updateRemainDays();
//            }
//        }
//    }
//}
