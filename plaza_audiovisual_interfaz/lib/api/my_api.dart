import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/utils/dialogs.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';

import '../model/user.dart';

class MyApi {
  MyApi._internal();
  static MyApi _instance = MyApi._internal();
  static MyApi get instance => _instance;

  final Dio _dio = Dio(
    BaseOptions(baseUrl: 'http://192.168.73.201:8080/rest/')
  );

  Future<void> register({required User user, required BuildContext context}) async {

    final ProgressDialog progressDialog = ProgressDialog(context);
    try {
      progressDialog.show();
      final Response response = await _dio.post(
          'users/',
          data: {
            "userername": user.userName,
            "name": user.name,
            "lastName": user.lastName,
            "email": user.email,
            "phoneNumber": user.phoneNumber,
            "password": user.pasword,
            "passwordConfirm": user.passwordConfirm,
            "description": user.description
          }
      );
      progressDialog.dissmiss();
      Navigator.pushNamedAndRemoveUntil(
          context!,
          LoginPage.routeName,
          (_) => false,
      );
    } catch(e) {

      progressDialog.dissmiss();
      if (e is DioError) {
        print(e.response?.statusCode);
        print(e.response?.data);
        Dialogs.info(context, title: "ERROR",content: "Username already exists!");
      } else {
        print(e);
      }
    }
  }

  Future<void> login({required User user, required BuildContext context}) async {

    final ProgressDialog progressDialog = ProgressDialog(context);
    try {
      progressDialog.show();
      final Response response = await _dio.post(
          'users/login',
          data: {
            "username": user.userName,
            "password": user.pasword
          }
      );
      progressDialog.dissmiss();
      Navigator.pushNamedAndRemoveUntil(
        context!,
        HomePage.routeName,
            (_) => false,
      );
    } catch(e) {

      progressDialog.dissmiss();
      if (e is DioError) {
        print(e.response?.statusCode);
        print(e.response?.data);
        Dialogs.info(context, title: "ERROR",content: "Credentials are not correct!");
      } else {
        print(e);
      }
    }
  }

  Future<dynamic> getUserInfo({required String username}) async {
    try {
      final Response response = await this._dio.get(
        'users/name/$username'
        );
      return response.data;
    }
    catch (e) {
      print(e);
      return null;
    }
  }
}