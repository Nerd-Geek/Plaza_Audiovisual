
import 'dart:io';
import 'dart:typed_data';

import 'package:image_picker/image_picker.dart';
import 'package:plaza_audiovisual_interfaz/model/login.dart';
import 'package:plaza_audiovisual_interfaz/model/media.dart';
import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/nav_bar.dart';
import 'package:plaza_audiovisual_interfaz/utils/dialogs.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:plaza_audiovisual_interfaz/utils/extras.dart';

import '../model/user.dart';
import '../utils/auth.dart';

const baseUrl = 'http://carlosmoreno.duckdns.org:9090/rest/';

class MyApi {
  MyApi._internal();
  static MyApi _instance = MyApi._internal();
  static MyApi get instance => _instance;

  final Dio _dio = Dio(
    BaseOptions(baseUrl: baseUrl)
  );

  Future<void> register({required User user, required BuildContext context}) async {

    final ProgressDialog progressDialog = ProgressDialog(context);
    try {
      progressDialog.show();
      final Response response = await _dio.post(
          'users/',
          data: {
            "username": user.username!.value,
            "name": user.name!.value,
            "lastName": user.lastName!.value,
            "email": user.email!.value,
            "phoneNumber": user.phoneNumber!.value,
            "password": user.pasword!.value,
            "passwordConfirm": user.passwordConfirm!.value,
            "description": user.description!.value
          },
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
        Dialogs.info(context, title: "ERROR",content: "Username already exists!");
      } else {
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
      Session session = Session(
          token: response.data['token'],
          expirate: response.data['expirateToken'],
          createdAt: DateTime.now()
      );
      await Auth.instance.setSession(session);
      //String? token = await Auth.instance.getToken();
      progressDialog.dissmiss();
      Navigator.pushNamedAndRemoveUntil(
        context!,
        NavBar.routeName,
            (_) => false,
      );
    } catch(e) {

      progressDialog.dissmiss();
      if (e is DioError) {
        Dialogs.info(context, title: "ERROR",content: "Credentials are not correct!");
      } else {
      }
    }
  }

  Future<User?> getUserInfo({required username}) async {
    try {
      String? token = "";
      await Auth.instance.getToken().then((value) =>  token = value);
      final Response response = await _dio.get(
          'users/name/$username',

          options: Options(
              headers: {
                'Authorization': 'Bearer $token',
              }
          )
      );
      User user = User.fromJson(response.data);
      return user;
    } catch(e) {
      return null;
    }
  }

  Future<String?> deleteMedia(BuildContext context,{required String id}) async {
    try {
      String? token = "";
      await Auth.instance.getToken().then((value) =>  token = value);
      final Response response = await _dio.delete(
          'media/$id',

          options: Options(
              headers: {
                'Authorization': 'Bearer $token',
              }
          )
      );
      Dialogs.info(context, title: "Delete media",content: "Delete media!");
      return id;
    } catch(e) {
      return null;
    }
  }

  Future<void> updateUser({required User user, required BuildContext context}) async {
    final ProgressDialog progressDialog = ProgressDialog(context);
    try {
      progressDialog.show();
      String? token = await Auth.instance.getToken()??"";

      final Response response = await _dio.put(
          'users/me',

          options: Options(
              headers: {
                'Authorization': 'Bearer $token',
              }
          ),
        data: {
          "username": user.username!.value,
          "name": user.name!.value,
          "lastName": user.lastName!.value,
          "email": user.email!.value,
          "phoneNumb er": user.phoneNumber!.value,
          "password": user.pasword!.value,
          "passwordConfirm": user.passwordConfirm!.value,
          "description": user.description!.value
        },
      );
      progressDialog.dissmiss();
      Navigator.pushNamedAndRemoveUntil(
        context,
        NavBar.routeName,
            (_) => false,
      );
    } catch(e) {
      print(e);
      progressDialog.dissmiss();
    }
  }

  Future<List<User>?> getMediasUser() async {
    try {
      String token = await Auth.instance.getToken()??"";
      var response = await _dio.get(
        'users/',

        options: Options(
          headers: {
            'Authorization': 'Bearer $token',
          }
        ),
      );
      List<User> users = [];
      for (int i = 0; i < response.data.length; i++) {
        users.add(User.fromJson(response.data[i]));
        print(users[i].image);
      }
      return users;
    }
    catch (e) {
      return null;
    }
  }

  Future<String?> getUsername() async {
    try {
      String? token = await Auth.instance.getToken()??"";
      String url = 'logins/token/$token';
      var response = await _dio.get(
        url,
        options: Options(
            headers: {
              'Authorization': 'Bearer $token',
            }
        ),
      );
      String username = response.data["user"]["username"];
      return username;
    }
    catch (e) {
      return e.toString();
    }
  }

  Future<String> setImageAvatar(String path, BuildContext context) async {
    try {
      String? token = await Auth.instance.getToken()??"";
      String url = 'users/avatar';
      FormData formData = FormData();
      formData.files.add(
          MapEntry(
            "file",
            await MultipartFile.fromFile(path,
              filename: Extras.getFileName(path))));
      final Response response = await _dio.post(
        url,

        options: Options(
            headers: {
              'Authorization': 'Bearer $token',
              'Content-Type': 'multipart/form-data;file',
              'Accept': '*/*',
            },

        ),
        data: formData,
      );
      Dialogs.info(context, title: "Modify",content: "Modify avatar!");
      return response.data;
    }
    catch (e) {
      return e.toString();
    }
  }

  Future<String> setMedia(Uint8List bytes, String path, String description, BuildContext context) async {
    try {
      print(description);
      String? token = await Auth.instance.getToken()??"";
      String url = 'media/create';
      var formData = FormData.fromMap({
        'description': description,
        'file':  await MultipartFile.fromFile(path,
                   filename: Extras.getFileName(path))
      });

      final Response response = await _dio.post(
        url,

        options: Options(
          headers: {
            'Authorization': 'Bearer $token',
            'Content-Type': 'multipart/form-data;boundary=description',
          },

        ),
        data:  formData,
      );
      Dialogs.info(context, title: "Create media",content: "Create media!");
      return response.data;
    }
    catch (e) {
      print(e);
      return e.toString();
    }
  }
}