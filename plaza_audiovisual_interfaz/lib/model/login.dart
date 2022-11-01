import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';

import 'media.dart';

class Login {

  String? id;
  Rx<User> user = User().obs;
  String? password;
  String? token;
  int? instance;

  Login({
    this.id,
    required this.user,
    this.token,
    this.instance,
  });

  Login.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    token = json['token'];
    instance = json['instance'];
    user.value = json['user'];

  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;

    return data;
  }
}