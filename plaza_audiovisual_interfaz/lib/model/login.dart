import 'package:plaza_audiovisual_interfaz/model/user.dart';

import 'media.dart';

class Login {

  String? id;
  String? userName;
  String? name;
  String? lastName;
  String? email;
  String? phoneNumber;
  String? image;
  String? description;
  Set<String>? roles;
  Set<Media>? medias;
  String? token;
  int? expirateToken;

  Login({
    this.id,
    this.userName,
    this.name,
    this.lastName,
    this.email,
    this.phoneNumber,
    this.image,
    this.description,
    this.roles,
    this.medias,
    this.token,
    this.expirateToken,
  });

  Login.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    userName = json['userName'];
    name = json['name'].obs;
    lastName = json['lastName'].obs;
    email = json['email'].obs;
    phoneNumber = json['phoneNumber'].obs;
    image = json['image'].obs;
    description = json['description'].obs;
    medias = json['roles'].obs;
    medias = json['medias'].obs;
    medias = json['token'].obs;
    medias = json['expirateToken'].obs;

  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;

    return data;
  }
}