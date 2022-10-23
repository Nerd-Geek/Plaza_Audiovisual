import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/model/user_rol.dart';

import 'media.dart';

class User {

  RxString? id = "".obs;
  RxString? username = "".obs;
  RxString? name = "".obs;
  RxString? lastName = "".obs;
  RxString? email = "".obs;
  RxString? phoneNumber = "".obs;
  RxString? pasword = "".obs;
  RxString? passwordConfirm = "".obs;
  RxString? image = "".obs;
  RxString? description = "".obs;
  RxList<UserRol>? roles = <UserRol>[].obs;
  RxList<Media>? medias = <Media>[].obs;

  User({
    this.id,
    this.username,
    this.name,
    this.lastName,
    this.email,
    this.phoneNumber,
    this.pasword,
    this.passwordConfirm,
    this.image,
    this.description,
    this.medias
  });

  User.fromJson(Map<String, dynamic> json) {
    id!.value = json['id'];
    username!.value = json['username'];
    name!.value = json['name'];
    lastName!.value = json['lastName'];
    email!.value = json['email'];
    phoneNumber!.value = json['phoneNumber'];
    image!.value = json['image'];
    description!.value = json['description'];
    if (json['medias'] != null || (json['medias'] as List).isNotEmpty) {
      medias!.value = <Media>[];
      json['medias'].forEach((v) {
        medias!.add(Media.fromJson(v));
      });
    }

  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;

    return data;
  }

  parseResult(List<dynamic> data) {
    List<User> results = <User>[];
    data.forEach((item) {
      results.add(User.fromJson(item));
    });
    return results;
  }

  
}