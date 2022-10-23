import 'dart:ffi';

import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';

class Media {
  RxString? id = "".obs;
  int? size;
  RxString? type = "".obs;
  RxString? name = "".obs;
  RxString? description = "".obs;
  int? dimension;

  Media({
    this.id,
    this.size,
    this.type,
    this.name,
    this.description,
    this.dimension,
  });

  Media.fromJson(Map<String, dynamic> json) {
    id!.value = json['id'];
    size = json['size'];
    type!.value = json['type'];
    name!.value = json['name'];
    description!.value = json['description'];
    dimension = json['dimension'];
  }
}