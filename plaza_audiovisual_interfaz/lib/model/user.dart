import 'media.dart';

class User {

  String? id;
  String? userName;
  String? name;
  String? lastName;
  String? email;
  String? phoneNumber;
  String? pasword;
  String? passwordConfirm;
  String? image;
  String? description;
  Set<Media>? medias;

  User({
    this.id,
    this.userName,
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
    id = json['id'];
    userName = json['username'];
    name = json['name'].obs;
    lastName = json['lastName'].obs;
    email = json['email'].obs;
    phoneNumber = json['phoneNumber'].obs;
    pasword = json['pasword'].obs;
    passwordConfirm = json['passwordConfirm'].obs;
    image = json['image'].obs;
    description = json['description'].obs;
    medias = json['medias'].obs;

  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;

    return data;
  }

  
}