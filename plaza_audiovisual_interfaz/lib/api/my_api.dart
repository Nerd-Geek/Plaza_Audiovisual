

import 'dart:convert';

import 'package:plaza_audiovisual_interfaz/model/media.dart';
import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/nav_bar.dart';
import 'package:plaza_audiovisual_interfaz/utils/dialogs.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';

import '../model/user.dart';
import '../utils/auth.dart';

class MyApi {
  MyApi._internal();
  static MyApi _instance = MyApi._internal();
  static MyApi get instance => _instance;

  final Dio _dio = Dio(
    BaseOptions(baseUrl: 'http://192.168.29.141:6668/rest/')
  );

  Future<void> register({required User user, required BuildContext context}) async {

    final ProgressDialog progressDialog = ProgressDialog(context);
    try {
      progressDialog.show();
      final Response response = await _dio.post(
          'users/',
          data: {
            "userername": user.username!.value,
            "name": user.name!.value,
            "lastName": user.lastName!.value,
            "email": user.email!.value,
            "phoneNumber": user.phoneNumber!.value,
            "password": user.pasword!.value,
            "passwordConfirm": user.passwordConfirm!.value,
            "description": user.description!.value
          },
          // options: Options(
          //   headers: {
          //     'Authorization': 'Bearer $token',
          //   }
          // ),

      );
      //await Auth.instance.setSession(response.data);
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
            "username": user.username!.value,
            "password": user.pasword!.value
          }

      );
      final Session session = Session(
          token: response.data['token'],
          expirate: response.data['expirateToken'],
          createdAt: DateTime.now()
      );
      await Auth.instance.setSession(session);
      progressDialog.dissmiss();
      Navigator.pushNamedAndRemoveUntil(
        context!,
        NavBar.routeName,
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
      final Response response = await _dio.get(
        'users/name/$username',
      );
      return response.data;
    }
    catch (e) {
      print(e);
      return null;
    }
  }
  Future<List<User>?> getMediasUser() async {
    try {
      var response = await _dio.get(
        'users/',
      );
      List<User> users = [];
      for (int i = 0; i < response.data.length; i++) {
        users.add(User.fromJson(response.data[i]));
      }
      return users;
    }
    catch (e) {
      print(e);
      return null;
    }
  }
}