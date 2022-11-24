import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';




class Auth {
  Auth._internal();
  static Auth _instance = Auth._internal();
  static Auth get instance => _instance;

  final storage = const FlutterSecureStorage();
  final key = "SESSION";

  Future<String?> accessToken(BuildContext context) async {
    final Session? session = await getSession();
    if(session != null){
      final DateTime currentDate = DateTime.now();
      final DateTime createAt = session.createdAt;
      final int sessionEx = session.expirate;
      final int diff = currentDate.difference(createAt).inMilliseconds;
      if(sessionEx - diff >=  60000) {
        return session.token;
      } else {
        await logOut(context);
        return null;
      }

    }
    return null;
  }

  Future<String?> getToken() async {
    final Session? session = await getSession();
    if(session != null){
      String token = session.token.obs.value.toString();
      return token;
    }
    return null;
  }

  Future<void> setSession(Session session) async {
    final String value = jsonEncode(session.toJson());
    await storage.write(key: key, value: value);
  }

  Future<Session?> getSession() async{
    final String? value = await storage.read(key: key);

    if(value != null) {
      final Map<String, dynamic> json = jsonDecode(value);
      final session = Session.fromJson(json);
      return session;
    }
    return null;
  }

  Future<void> logOut(BuildContext context) async{
    await storage.deleteAll();
    Navigator.pushNamedAndRemoveUntil(
        context,
        LoginPage.routeName,
        (_) => false);
  }
}

class Session {
  final String token;
  final int expirate;
  final DateTime createdAt;

  Session({
    required this.token,
    required this.expirate,
    required this.createdAt
  });

  static Session fromJson(Map<String, dynamic> json) {
    return Session(
      token: json['token'],
      expirate: json['expirate'],
      createdAt: DateTime.parse(json['createdAt']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "token":token,
      "expirate":expirate,
      "createdAt":createdAt.toString(),
    };
  }
}