
import 'package:flutter/cupertino.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';

class DataState with ChangeNotifier {
  bool isLoading = false;
  int totalPage = 0;
  int currentPage = 0;
  List<User> items = [];
}