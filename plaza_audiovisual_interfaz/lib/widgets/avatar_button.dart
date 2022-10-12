import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AvatarButton extends StatelessWidget {
  final double imageSize;

  AvatarButton({this.imageSize = 100});

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          margin: EdgeInsets.all(10),
          decoration: const BoxDecoration(
            color: Colors.white,
            shape: BoxShape.circle,
            boxShadow: [
              BoxShadow(
                  offset: Offset(0,20),
                  blurRadius: 20,
                  color: Colors.black26),
            ],
          ),
          child: ClipOval(
            child: Image.network(
              "https://www.w3schools.com/howto/img_avatar2.png",
              width: imageSize,
              height: imageSize,
            ),
          ),
        ),
        Positioned(
            bottom: 5,
            right: 0,
            child: CupertinoButton(
                padding: EdgeInsets.zero,
                borderRadius: BorderRadius.circular(30),
                onPressed: () {
                },
                child: Container(
                  child: Icon(
                      Icons.add,
                      color : Colors.white,
                  ),
                  padding: const EdgeInsets.all(3),
                  decoration: BoxDecoration(
                      border: Border.all(
                        color: Colors.white,
                        width: 2,
                      ),
                      color: Colors.pinkAccent,
                      shape: BoxShape.circle
                  ),
                )
            )
        ),

      ],
    );
  }

}