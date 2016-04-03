package service.test;

import service.UserServices;

public class TestLikeService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(UserServices.like("7e258cadc13c406496ae93b89dec575d", "56fea976e4b047655abf02b2"));
		System.out.println(UserServices.dislike("7e258cadc13c406496ae93b89dec575d", "56fea976e4b047655abf02b2"));

	}

}
